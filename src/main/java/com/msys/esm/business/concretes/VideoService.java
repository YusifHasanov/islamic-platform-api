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
import com.msys.esm.dataAccess.VideoRepository;
import com.msys.esm.entities.Video;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VideoService implements IVideoService {

    VideoRepository videoRepository;
    ModelService mapper;
    static YouTube youTube;
    static YouTube.Search.List request;
    static YouTube.Playlists.List requestPlayList;
    static YouTube.PlaylistItems.List requestPlayListItemList;
    static ArrayList<Video> ytVideoList;
    static SearchListResponse response;
    static PlaylistListResponse responsePlayList;
    static PlaylistItemListResponse responsePlayListItemList;

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
                    .setPlaylistId("PLU43-RoCoSfNG4hFDOwsh3TrRljtbuezZ")
                    .setMaxResults(50L)
                    .execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ytVideoList = new ArrayList<>();
    }

    @Override
    public ResponseEntity<List<VideoResponse>> getAll() {
        List<Video> videos = videoRepository.findAll();
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class)).toList();
        return ResponseEntity.ok(responseVideos);
    }

    @Override
    public ResponseEntity<VideoResponse> getById(int id) {
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
    public ResponseEntity<VideoResponse> delete(int id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + id));
        videoRepository.deleteById(id);
        VideoResponse videoREsponse = mapper.forResponse().map(video, VideoResponse.class);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(videoREsponse);
    }

    @Override
    public ResponseEntity<UpdateVideo> update(UpdateVideo video, int id) {
        Video findedVideo = videoRepository.findById(video.getId())
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + video.getId()));
//        CheckIds.check(findedVideo.getId(), id);
        videoRepository.save(mapper.forRequest().map(video, Video.class));
        return ResponseEntity.ok(video);
    }

    @Override
    public ResponseEntity<List<VideoResponse>> getByPlaylistId(String playlistId) {
        List<Video> videos = videoRepository.findVideosByPlaylistPlaylistId(playlistId);
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class)).toList();
        return ResponseEntity.ok(responseVideos);
    }

    @Override
    public void addVideosToDb() {
        try {
            List<Video> videos = new ArrayList<>();
            for (PlaylistItem video : getVideos()) {
                Video v = new Video();
                v.setVideoId(video.getId());
                v.setTitle(video.getSnippet().getTitle());
                v.setPlaylist(new com.msys.esm.entities.Playlist());
                if (video.getSnippet().getThumbnails().getMedium() != null)
                    v.setThumbnail(video.getSnippet().getThumbnails().getMedium().getUrl());
                else
                    v.setThumbnail("");
                videos.add(v);
            }
            videoRepository.saveAll(videos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Playlist> getPlayLists() throws IOException {
//        if (videoRepository.findAll().size()>851){
//            return;
//        }
//        if (response.getItems().size() == response.getPageInfo().getResultsPerPage()) {
//            do {
////                response.getItems().forEach(video ->
////                        ytVideoList.add(this.mapper.forResponse().map(video, Video.class)));
//                for (SearchResult item : response.getItems()) {
//                    ytVideoList.add(new Video(0,item.getId().getVideoId(),new Date(2L),item.getSnippet().getThumbnails().toPrettyString(),item.getSnippet().getTitle(),new Playlist()));
//                }
//                videoRepository.saveAll(ytVideoList);
//                System.out.println("Total video: "+videoRepository.findAll().size());
//                response = request.setPageToken(response.getNextPageToken()).execute();
//
//            } while (response.getNextPageToken() != null);
//        } else {
//            response = sendRequest();
//            doWork();
//        }
        List<Playlist> playlists = new ArrayList<>();
        String nextPage = null;
        do {
            requestPlayList.setPageToken(nextPage);
            responsePlayList = requestPlayList.execute();
            playlists.addAll(responsePlayList.getItems());
            nextPage = responsePlayList.getNextPageToken();
        }
        while (nextPage != null);
        return playlists;
    }

    public List<PlaylistItem> getVideos() throws IOException {

        String nextPage = null;
        List<Playlist> playlistList = getPlayLists();
        List<PlaylistItem> videoList = new ArrayList<>();

        for (Playlist playlist : playlistList) {

            do {
                responsePlayListItemList = requestPlayListItemList.setPlaylistId(playlist.getId()).setPageToken(nextPage).execute();
                videoList.addAll(responsePlayListItemList.getItems());
//                System.out.println("List size: " + videoList.size());
                nextPage = responsePlayListItemList.getNextPageToken();
            }
            while (nextPage != null);

//            System.out.println("Playlist name: " + playlist.getSnippet().getTitle() + " | Total video: " + responsePlayListItemList.getPageInfo().getTotalResults());

        }

        return videoList;

    }

    public static SearchListResponse sendRequest() throws IOException {
        return request
                .setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                .setChannelId(ConnectYoutubeApi.CHANNEL_ID)
                .setMaxResults(50L)
                .setOrder("date")
                .setType("video")
//                .setPublishedBefore(new DateTime("2019-02-03T13:06:10.000Z"))
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
