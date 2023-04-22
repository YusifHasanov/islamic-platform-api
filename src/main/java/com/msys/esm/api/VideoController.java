package com.msys.esm.api;

import com.msys.esm.business.concretes.PlaylistService;
import com.msys.esm.business.concretes.VideoService;
import com.msys.esm.core.dto.Request.Create.CreateVideo;
import com.msys.esm.core.dto.Request.Update.UpdateVideo;
import com.msys.esm.core.dto.Response.VideoResponse;
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

    private final VideoService service;
    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping(params = "playlistId")
    public ResponseEntity<List<VideoResponse>> getByPlaylistId(@RequestParam("playlistId") String playlistId) {
        return service.getByPlaylistId(playlistId);
    }

    @PostMapping
    public ResponseEntity<CreateVideo> add(@Valid @RequestBody CreateVideo Video) {
        return service.add(Video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateVideo> update(@Valid @RequestBody UpdateVideo video, @PathVariable String id) {
        return service.update(video, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VideoResponse> delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PutMapping
    public void updateVideos() {
        service.addOrUpdateVideos();
    }

    @GetMapping(params = {"sortBy", "sortOrder"})
    public ResponseEntity<List<VideoResponse>> getAllSorted(@RequestParam(name = "sortBy") String fieldName, @RequestParam(name = "sortOrder") String sortOrder) {
        return service.getSortedVideosBySpecificField(fieldName, sortOrder);
    }

    @GetMapping(params = {"playlistId", "sortBy", "sortOrder"})
    public ResponseEntity<List<VideoResponse>> getPlayListVideosSorted(@RequestParam(name = "sortBy") String fieldName, @RequestParam String sortOrder, @RequestParam String playlistId) {
        return service.getSortedPlayListVideosBySpecificField(fieldName, playlistId, sortOrder);
    }

    @GetMapping(params = {"playlistId", "page", "maxResult"})
    public ResponseEntity<?> getAllPaged(@RequestParam String playlistId,@RequestParam int page,@RequestParam(name = "maxResult") int size) {
        return service.getByPlaylistIdAndPagination(playlistId, page, size);
    }
}
