package com.example.api.api;

import com.example.api.core.dto.Response.PlaylistResponse;
import com.example.api.core.util.mapper.concretes.ModelService;
import com.example.api.dataAccess.PlaylistRepository;
import com.example.api.dataAccess.VideoRepository;
import com.example.api.entities.Playlist;
import com.example.api.entities.Video;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class RandomController {
    VideoRepository videoRepository;
    PlaylistRepository playlistRepository;
    ModelService mapper;
    @GetMapping
    public List<Video> getAll() {
        return videoRepository.findAll();
    }
    @GetMapping("/videoPlaylist/{playlistId}")
    public List<Video> getAllVideosForPlaylist(@PathVariable String playlistId) {
        return videoRepository.findVideosByPlaylistPlaylistId(playlistId);
    }
    @GetMapping("/playlists")
    public List<PlaylistResponse> getAllPlaylists() {

        return playlistRepository.findAll().stream()
                .map(playlist -> mapper.forResponse()
                        .map(playlist, PlaylistResponse.class)).toList();

    }
}
