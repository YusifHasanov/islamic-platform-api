package com.msys.esm.business.concretes;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.msys.esm.business.abstracts.IPlaylistService;
import com.msys.esm.core.dto.Request.Create.CreatePlaylist;
import com.msys.esm.core.dto.Request.Update.UpdatePlaylist;
import com.msys.esm.core.dto.Response.PlaylistResponse;
import com.msys.esm.core.util.ConnectYoutubeApi;
import com.msys.esm.core.util.Exceptions.PlaylistNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.PlaylistRepository;
import com.msys.esm.dataAccess.VideoRepository;
import com.msys.esm.entities.Playlist;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlaylistService implements IPlaylistService {

    PlaylistRepository playlistRepository;
    ModelService mapper;
    VideoRepository videoRepository;
    private static YouTube youTube;
    private static YouTube.Playlists.List requestPlayList;
    private static PlaylistListResponse responsePlayList;

    static {

        youTube = ConnectYoutubeApi.youtubeService;

        try {
            requestPlayList = youTube.playlists().list("snippet");
            responsePlayList = sendRequestPlayList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<List<PlaylistResponse>> getAll() {
        List<Playlist> playlists = playlistRepository.findAll();
        List<PlaylistResponse> responsePlaylists = playlists.stream()
                .map(p -> mapper.forResponse().map(p, PlaylistResponse.class)).toList();
        return ResponseEntity.ok(responsePlaylists);
    }

    @Override
    public ResponseEntity<PlaylistResponse> getById(String id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        PlaylistResponse response = mapper.forResponse().map(playlist, PlaylistResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreatePlaylist> add(CreatePlaylist playlist) {
        Playlist mappedPlaylist = mapper.forRequest().map(playlist, Playlist.class);
        playlistRepository.save(mappedPlaylist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @Override
    public void updatePlaylists() {

        List<com.google.api.services.youtube.model.Playlist> playlistsFromYT = new ArrayList<>();
        String nextPage = null;

        do {

            requestPlayList.setPageToken(nextPage);
            try {
                responsePlayList = requestPlayList.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            playlistsFromYT.addAll(responsePlayList.getItems().stream()
                    .filter(playlistItem -> playlistItem.getSnippet().getThumbnails().getMedium() != null)
                    .toList());

            nextPage = responsePlayList.getNextPageToken();

        } while (nextPage != null);

        List<Playlist> playlists = playlistsFromYT.stream()
                .map(playlist -> {

                    Playlist pl = new Playlist();

                    pl.setPlaylistId(playlist.getId());
                    pl.setTitle(playlist.getSnippet().getTitle());
                    pl.setPublishedAt(playlist.getSnippet().getPublishedAt().toString());
                    pl.setThumbnail(playlist.getSnippet().getThumbnails().getDefault().getUrl() + "+" +
                            playlist.getSnippet().getThumbnails().getMedium().getUrl() + "+" +
                            playlist.getSnippet().getThumbnails().getHigh().getUrl());
                    pl.setVideoCount(videoRepository.findAllByPlaylistId(playlist.getId()).size());
                    System.out.println(videoRepository.findAllByPlaylistId(playlist.getId()).size());
                    System.out.println(videoRepository.findAllByPlaylistId(playlist.getId()));
                    System.out.println(pl.getVideoCount());
                    return pl;

                }).toList();

        playlistRepository.saveAll(playlists);
    }

    @Override
    public ResponseEntity<PlaylistResponse> delete(String id) {

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        playlistRepository.deleteById(id);
        PlaylistResponse response = mapper.forResponse().map(playlist, PlaylistResponse.class);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Override
    public ResponseEntity<UpdatePlaylist> update(UpdatePlaylist playlist, String playlistId) {

        Playlist updatePlaylist = playlistRepository.findById(playlist.getPlaylistId())
                .orElseThrow(() ->
                        new PlaylistNotFoundException("Playlist not found with id: " + playlist.getPlaylistId()));
        CheckIds.checkForPlayListOrVideo(updatePlaylist.getPlaylistId(), playlistId);
        Playlist mappedPlaylist = mapper.forRequest().map(playlist, Playlist.class);

        playlistRepository.save(mappedPlaylist);

        return ResponseEntity.ok(playlist);

    }

    public static PlaylistListResponse sendRequestPlayList() throws IOException {

        return requestPlayList
                .setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                .setChannelId(ConnectYoutubeApi.CHANNEL_ID)
                .setMaxResults(50L)
                .execute();

    }

}