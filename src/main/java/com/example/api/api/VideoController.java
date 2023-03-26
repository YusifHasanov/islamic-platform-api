package com.example.api.api;

import com.example.api.business.concretes.VideoService;
import com.example.api.core.dto.POST.VideoPost;
import com.example.api.core.dto.PUT.VideoPut;
import com.example.api.entities.Video;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    
    private final VideoService videoService;

    @GetMapping

    public ResponseEntity<List<Video>> getAll() {
        return videoService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(@PathVariable int id) {
        return videoService.getById(id);
    }

    @PostMapping
    public ResponseEntity<VideoPost> add(@Valid @RequestBody VideoPost Video) {
        return videoService.add(Video);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VideoPut> update(@Valid @RequestBody VideoPut Video , @PathVariable int id) {
        return videoService.update(Video);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Video> delete(@PathVariable int id) {
        return videoService.delete(id);
    }
}
