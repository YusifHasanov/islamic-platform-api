package com.example.api.api;

import com.example.api.business.concretes.VideoService;
import com.example.api.core.dto.Request.Create.CreateVideo;
import com.example.api.core.dto.Request.Update.UpdateVideo;
import com.example.api.core.dto.Response.VideoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VideoController {
    
    private final VideoService videoService;

    @GetMapping

    public ResponseEntity<List<VideoResponse>> getAll() {
        return videoService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getById(@PathVariable int id) {
        return videoService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateVideo> add(@Valid @RequestBody CreateVideo Video) {
        return videoService.add(Video);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateVideo> update(@Valid @RequestBody UpdateVideo video) {
        return videoService.update(video);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<VideoResponse> delete(@PathVariable int id) {
        return videoService.delete(id);
    }
}
