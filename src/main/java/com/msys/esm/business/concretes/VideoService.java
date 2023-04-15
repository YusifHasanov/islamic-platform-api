package com.msys.esm.business.concretes;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.msys.esm.business.abstracts.IVideoService;
import com.msys.esm.core.dto.Request.Create.CreateVideo;
import com.msys.esm.core.dto.Request.Update.UpdateVideo;
import com.msys.esm.core.dto.Response.VideoResponse;
import com.msys.esm.core.util.ConnectYoutubeApi;
import com.msys.esm.core.util.Exceptions.VideoNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.VideoRepository;
import com.msys.esm.entities.Video;
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
public class VideoService implements IVideoService {

    VideoRepository videoRepository;
    ModelService mapper;
    private static YouTube youTube;
    private static YouTube.Search.List request;
    private static YouTube.Playlists.List requestPlayList;
    private static YouTube.PlaylistItems.List requestPlayListItemList;
    private static SearchListResponse response;
    private static PlaylistListResponse responsePlayList;
    private static PlaylistItemListResponse responsePlayListItemList;

    static {

        youTube = ConnectYoutubeApi.youtubeService;

        try {


            request = youTube.search().list("snippet");
            requestPlayList = youTube.playlists().list("snippet");
            responsePlayList = sendRequestPlayList();
            response = sendRequest();
            requestPlayListItemList = youTube.playlistItems().list("snippet");
            responsePlayListItemList = requestPlayListItemList
                    .setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                    .setPlaylistId("PLU43-RoCoSfNG4hFDOwsh3TrRljtbuezZ").setMaxResults(50L).execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<List<VideoResponse>> getAll() {
        List<Video> videos = videoRepository.findAll();
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class)).toList();
        return ResponseEntity.ok(responseVideos);
    }

    @Override
    public ResponseEntity<VideoResponse> getById(String id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + id));
        return ResponseEntity.ok(mapper.forResponse().map(video, VideoResponse.class));
    }

    @Override
    public ResponseEntity<CreateVideo> add(CreateVideo video) {
        Video mappedVideo = mapper.forRequest().map(video, Video.class);
        videoRepository.save(mappedVideo);
        return ResponseEntity.status(HttpStatus.CREATED).body(video);
    }

    @Override
    public ResponseEntity<VideoResponse> delete(String id) {

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + id));

        videoRepository.deleteById(id);

        VideoResponse videoREsponse = mapper.forResponse().map(video, VideoResponse.class);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(videoREsponse);

    }

    @Override
    public ResponseEntity<UpdateVideo> update(UpdateVideo video, String id) {

        Video findedVideo = videoRepository.findById(video.getVideoId())
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + video.getVideoId()));

        CheckIds.checkForPlayListOrVideo(findedVideo.getVideoId(), id);

        videoRepository.save(mapper.forRequest().map(video, Video.class));

        return ResponseEntity.ok(video);
    }

    @Override
    public ResponseEntity<List<VideoResponse>> getByPlaylistId(String playlistId) {

        List<Video> videos = videoRepository.findVideosByPlaylistId(playlistId);
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class))
                .toList();

        return ResponseEntity.ok(responseVideos);

    }

    @Override
    public void addOrUpdateVideos() {

        try {

            List<Video> videos = getAllVideosFromYouTubeAPI().stream()
                    .map(video -> {

                        Video video1 = mapper.forResponse().map(video, Video.class);

                        video1.setThumbnail(video.getSnippet().getThumbnails().getDefault().getUrl() + "|" +
                                video.getSnippet().getThumbnails().getMedium().getUrl() + "|" +
                                video.getSnippet().getThumbnails().getHigh().getUrl());

                        return video1;

                    }).toList();
            videoRepository.saveAll(videos);
        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public List<Playlist> getPlayLists() throws IOException {

        List<Playlist> playlists = new ArrayList<>();
        String nextPage = null;

        do {

            requestPlayList.setPageToken(nextPage);
            responsePlayList = requestPlayList.execute();

            playlists.addAll(responsePlayList.getItems());

            nextPage = responsePlayList.getNextPageToken();

        } while (nextPage != null);

        return playlists;
    }

    public List<PlaylistItem> getAllVideosFromYouTubeAPI() throws IOException {

        String nextPage = null;
        List<Playlist> playlistList = getPlayLists();
        List<PlaylistItem> videoList = new ArrayList<>();

        for (Playlist playlist : playlistList) {

            do {

                responsePlayListItemList = requestPlayListItemList
                        .setPlaylistId(playlist.getId())
                        .setPageToken(nextPage).execute();

                videoList.addAll(responsePlayListItemList.getItems().stream()
                        .filter(playlistItem -> playlistItem.getSnippet().getThumbnails().getMedium() != null).toList());


                nextPage = responsePlayListItemList.getNextPageToken();

            } while (nextPage != null);


        }

        return videoList;

    }

    public static SearchListResponse sendRequest() throws IOException {
        return request.setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                .setChannelId(ConnectYoutubeApi.CHANNEL_ID)
                .setMaxResults(50L)
                .setOrder("date")
                .setType("video")
                .execute();
    }

    public static PlaylistListResponse sendRequestPlayList() throws IOException {
        return requestPlayList
                .setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                .setChannelId(ConnectYoutubeApi.CHANNEL_ID)
                .setMaxResults(50L)
                .execute();
    }


//    @Override
//    public ResponseEntity<List<VideoResponse>> getByPlaylistIdAndPageable(String playlistId, Pageable pageable) {
//        List<Video> pageableVideos = videoRepository.findVideosByPlaylistId(playlistId,pageable);
//          List<VideoResponse> response =pageableVideos.stream().map(v -> mapper.forResponse().map(v,VideoResponse.class)).toList();
//        return ResponseEntity.ok(response);
//    }
}
