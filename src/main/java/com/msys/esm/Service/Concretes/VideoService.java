package com.msys.esm.Service.Concretes;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.msys.esm.Core.DTO.Request.Create.CreateVideo;
import com.msys.esm.Core.DTO.Request.Update.UpdateVideo;
import com.msys.esm.Core.DTO.Response.PaginitionVideosResponse;
import com.msys.esm.Core.DTO.Response.VideoResponse;
import com.msys.esm.Core.Util.ConnectYoutubeApi;
import com.msys.esm.Core.Util.Exceptions.PlaylistNotFoundException;
import com.msys.esm.Core.Util.Exceptions.VideoNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Model.Video;
import com.msys.esm.Repository.VideoRepository;
import com.msys.esm.Service.Abstracts.IVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private static final YouTube.Search.List request;
    private static final YouTube.Playlists.List requestPlayList;
    private static final YouTube.PlaylistItems.List requestPlayListItemList;
    private static PlaylistListResponse responsePlayList;
    private static PlaylistItemListResponse responsePlayListItemList;

    static {

        YouTube youTube = ConnectYoutubeApi.youtubeService;

        try {

            request = youTube.search().list("snippet");
            requestPlayList = youTube.playlists().list("snippet");
            responsePlayList = sendRequestPlayList();
//            SearchListResponse response = sendRequest();
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

        VideoResponse videoResponse = mapper.forResponse().map(video, VideoResponse.class);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(videoResponse);

    }

    @Override
    public ResponseEntity<UpdateVideo> update(UpdateVideo video, String id) {

        Video findedVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + id));

        CheckIds.checkForPlayListOrVideo(findedVideo.getVideoId(), id);

        Video updatedVideo = mapper.forRequest().map(video, Video.class);

        updatedVideo.setVideoId(id);

        videoRepository.save(updatedVideo);

        return ResponseEntity.ok(video);
    }
    @Override
    public ResponseEntity<List<VideoResponse>> getByPlaylistId(String playlistId) {

        List<Video> videos = videoRepository.findAllByPlaylistId(playlistId);
        System.out.println(videos);
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class))
                .toList();

        return ResponseEntity.ok(responseVideos);

    }


    // Hər gün saat 08:00, 16:00, 21:00 da update edəcək
    @Override
    @Scheduled(cron = "0 0 8,16,21 ? * *")
    public void addOrUpdateVideos() {

        try {

            List<Video> videos = getAllVideosFromYouTubeAPI().stream()
                    .map(video -> {

                        Video video1 = mapper.forResponse().map(video, Video.class);
                        video1.setVideoId(video.getSnippet().getResourceId().getVideoId());

                        video1.setThumbnail(video.getSnippet().getThumbnails().getDefault().getUrl() + "+" +
                                video.getSnippet().getThumbnails().getMedium().getUrl() + "+" +
                                video.getSnippet().getThumbnails().getHigh().getUrl());

                        return video1;

                    }).toList();
            videoRepository.saveAll(videos);
        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public ResponseEntity<List<VideoResponse>> getSortedVideosBySpecificField(String fieldName, String sortOrder) {

        List<Video> videos = videoRepository.findAll(Sort.by(Sort.Direction.fromString(sortOrder), fieldName));
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class))
                .toList();

        return ResponseEntity.ok(responseVideos);

    }

    @Override
    public ResponseEntity<List<VideoResponse>> getSortedPlayListVideosBySpecificField(String fieldName, String playListId, String sortOrder) {

        List<Video> videos = videoRepository.findAllByPlaylistId(playListId, Sort.by(Sort.Direction.fromString(sortOrder), fieldName));
        List<VideoResponse> responseVideos = videos.stream()
                .map(v -> mapper.forResponse().map(v, VideoResponse.class))
                .toList();

        return ResponseEntity.ok(responseVideos);
    }

    @Override
    public ResponseEntity<?> getByPlaylistIdAndPagination(String playListId, int page, int size) {
        if(size <= 0){
            throw new IllegalArgumentException("Invalid maxResult value. maxResult cannot be zero or negative");

        }
        List<VideoResponse> totalVideoList = getByPlaylistId(playListId).getBody();
        int totalVideo = totalVideoList != null ? totalVideoList.size() : 0;
        if (totalVideo == 0){
            throw new PlaylistNotFoundException("Videos not found for given playlist id");
        }
        System.out.println(totalVideo / size);
        System.out.println(totalVideo / size == totalVideo);
        int totalPageCount = totalVideo / size == 1 ? 1 : totalVideo / size + 1;

        if (page > totalPageCount) {
            throw new IllegalArgumentException(String.format("Invalid number of pages. Maximum number of pages is %d",totalPageCount));
        }

        Pageable pageable = PageRequest
                .of(page-1,
                        Math.min(size, totalVideoList.size()),
                        Sort.by("publishedAt").descending());
        List<VideoResponse> videoResponses =
                videoRepository.findVideosByPlaylistId(playListId, pageable).stream()
                .map(video -> mapper.forResponse().map(video, VideoResponse.class))
                .toList();


        return new ResponseEntity<>(new PaginitionVideosResponse(
                videoResponses,
                videoResponses.size(),
                totalVideo,
                page,
                totalPageCount),
                HttpStatus.OK);
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

    /**
     * Bu metod youtube API-sinə bağlanıb özəl məntiq ilə kanaldakı bütün videoları gətirir.<p>
     * Aralarında gizli, silinmiş videoları götürmür. Həmçinin şort (short) videoları da gətirir.</p>
     *
     * @return List<PlaylistItem>
     * @throws IOException
     * @author Maqsud Safin
     * @since v1
     */
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

    public static PlaylistListResponse sendRequestPlayList() throws IOException {
        return requestPlayList
                .setKey(ConnectYoutubeApi.DEVELOPER_KEY)
                .setChannelId(ConnectYoutubeApi.CHANNEL_ID)
                .setMaxResults(50L)
                .execute();
    }

}