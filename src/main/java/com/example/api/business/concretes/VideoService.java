package com.example.api.business.concretes;

import com.example.api.business.abstracts.IVideoService;
import com.example.api.core.dto.POST.VideoPost;
import com.example.api.core.dto.PUT.VideoPut;
import com.example.api.core.util.mapper.concretes.ModelService;
import com.example.api.dataAccess.VideoRepository;
import com.example.api.entities.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoRepository videoRepository;
    private final ModelService mapper;

    @Override
    public ResponseEntity<List<Video>> getAll() {
        List<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }

    @Override
    public ResponseEntity<Video> getById(int id) {
        Video video = videoRepository.findById(id).orElse(null);
        if (video == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(video);
    }

    @Override
    public ResponseEntity<VideoPost> add(VideoPost video) {
        Video mappedVideo = mapper.forRequest().map(video, Video.class);
        videoRepository.save(mappedVideo);
        return ResponseEntity.ok(video);
    }

    @Override
    public ResponseEntity<Video> delete(int id) {
        Video video = videoRepository.findById(id).orElse(null);
        if (video == null)
            return ResponseEntity.notFound().build();
        videoRepository.deleteById(id);
        return ResponseEntity.ok(video);
    }

    @Override
    public ResponseEntity<VideoPut> update(VideoPut video) {
        Video findedVideo = videoRepository.findById(video.getId()).orElse(null);
        if (findedVideo == null)
            return ResponseEntity.notFound().build();
        videoRepository.save(mapper.forRequest().map(video, Video.class));
        return ResponseEntity.ok(video);
    }
}
