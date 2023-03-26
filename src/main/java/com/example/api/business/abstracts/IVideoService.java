package com.example.api.business.abstracts;

import com.example.api.core.dto.POST.VideoPost;
import com.example.api.core.dto.PUT.VideoPut;
import com.example.api.entities.Video;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVideoService {
    ResponseEntity<List<Video>> getAll();
    ResponseEntity<Video> getById(int id);
    ResponseEntity<VideoPost> add(VideoPost video);
    ResponseEntity<Video> delete(int id);
    ResponseEntity<VideoPut> update(VideoPut video);
}
