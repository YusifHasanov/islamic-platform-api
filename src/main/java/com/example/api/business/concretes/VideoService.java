package com.example.api.business.concretes;
import com.example.api.business.abstracts.IVideoService;
import com.example.api.core.dto.Request.Create.CreateVideo;
import com.example.api.core.dto.Request.Update.UpdateVideo;
import com.example.api.core.dto.Response.VideoResponse;
import com.example.api.core.util.Exceptions.VideoNotFoundException;
import com.example.api.core.util.mapper.concretes.ModelService;
import com.example.api.dataAccess.VideoRepository;
import com.example.api.entities.Video;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<UpdateVideo> update(UpdateVideo video) {
        Video findedVideo = videoRepository.findById(video.getId())
                .orElseThrow(() -> new VideoNotFoundException("Video not found with id: " + video.getId()));
        Video mappedVideo = mapper.forRequest().map(video, Video.class);
        videoRepository.save(mappedVideo);
        return ResponseEntity.ok(video);
    }
}
