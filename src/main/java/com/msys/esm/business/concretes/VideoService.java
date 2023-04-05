package com.msys.esm.business.concretes;

import com.msys.esm.business.abstracts.IVideoService;
import com.msys.esm.core.dto.Request.Create.CreateVideo;
import com.msys.esm.core.dto.Request.Update.UpdateVideo;
import com.msys.esm.core.dto.Response.VideoResponse;
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

import java.awt.print.Pageable;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VideoService implements IVideoService {

    VideoRepository videoRepository;
    ModelService mapper;

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
        CheckIds.check(findedVideo.getId(), id);
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

//    @Override
//    public ResponseEntity<List<VideoResponse>> getByPlaylistIdAndPageable(String playlistId, Pageable pageable) {
//        List<Video> pageableVideos = videoRepository.findVideosByPlaylistId(playlistId,pageable);
//          List<VideoResponse> response =pageableVideos.stream().map(v -> mapper.forResponse().map(v,VideoResponse.class)).toList();
//        return ResponseEntity.ok(response);
//    }
}
