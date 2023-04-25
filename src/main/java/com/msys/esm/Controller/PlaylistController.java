package com.msys.esm.Controller;

import com.msys.esm.Service.Concretes.PlaylistService;
import com.msys.esm.Core.DTO.Request.Create.CreatePlaylist;
import com.msys.esm.Core.DTO.Request.Update.UpdatePlaylist;
import com.msys.esm.Core.DTO.Response.PlaylistResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getAll() {
        return playlistService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getById(@PathVariable String id) {
        return playlistService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreatePlaylist> add(@Valid @RequestBody CreatePlaylist playlist) {
        return playlistService.add(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePlaylist> update(@Valid @RequestBody UpdatePlaylist playlist, @PathVariable String id) {

        return playlistService.update(playlist, id);
    }

    @PutMapping
    public void updateAll() {
        playlistService.updatePlaylists();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistResponse> delete(@PathVariable String id) {
        return playlistService.delete(id);
    }
}

