package com.msys.esm.Controller;

import com.msys.esm.Service.Concretes.VideoService;
import com.msys.esm.Core.DTO.Request.Create.CreateVideo;
import com.msys.esm.Core.DTO.Request.Update.UpdateVideo;
import com.msys.esm.Core.DTO.Response.VideoResponse;
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
    public ResponseEntity<VideoResponse> getById(@PathVariable String id) {
        return videoService.getById(id);
    }

    @GetMapping(params = "playlistId")
    public ResponseEntity<List<VideoResponse>> getByPlaylistId(@RequestParam("playlistId") String playlistId) {
        return videoService.getByPlaylistId(playlistId);
    }

    @PostMapping
    public ResponseEntity<CreateVideo> add(@Valid @RequestBody CreateVideo Video) {
        return videoService.add(Video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateVideo> update(@Valid @RequestBody UpdateVideo video, @PathVariable String id) {
        return videoService.update(video, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VideoResponse> delete(@PathVariable String id) {
        return videoService.delete(id);
    }

    @PutMapping
    public void updateVideos() {
        videoService.addOrUpdateVideos();
    }

    @GetMapping(params = {"sortBy", "sortOrder"})
    public ResponseEntity<List<VideoResponse>> getAllSorted(@RequestParam(name = "sortBy") String fieldName, @RequestParam(name = "sortOrder") String sortOrder) {
        return videoService.getSortedVideosBySpecificField(fieldName, sortOrder);
    }

    @GetMapping(params = {"playlistId", "sortBy", "sortOrder"})
    public ResponseEntity<List<VideoResponse>> getPlayListVideosSorted(@RequestParam(name = "sortBy") String fieldName, @RequestParam String sortOrder, @RequestParam String playlistId) {
        return videoService.getSortedPlayListVideosBySpecificField(fieldName, playlistId, sortOrder);
    }

    @GetMapping(params = {"playlistId", "page", "maxResult"})
    public ResponseEntity<?> getAllPaged(@RequestParam String playlistId,@RequestParam int page,@RequestParam(name = "maxResult") int size) {
        return videoService.getByPlaylistIdAndPagination(playlistId, page, size);
    }
}
