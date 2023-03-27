package com.example.api.business.concretes;

import com.example.api.business.abstracts.IPlaylistService;
import com.example.api.core.dto.Request.Create.CreatePlaylist;
import com.example.api.core.dto.Request.Update.UpdatePlaylist;
import com.example.api.core.dto.Response.PlaylistResponse;
import com.example.api.core.util.Exceptions.PlaylistNotFoundException;
import com.example.api.core.util.mapper.concretes.ModelService;
import com.example.api.dataAccess.PlaylistRepository;
import com.example.api.entities.Playlist;
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
public class PlaylistService implements IPlaylistService {
    PlaylistRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<PlaylistResponse>> getAll() {
        List<Playlist> playlists = repository.findAll();
        List<PlaylistResponse> responsePlaylists = playlists.stream()
                .map(p -> mapper.forResponse().map(p, PlaylistResponse.class)).toList();
        return ResponseEntity.ok(responsePlaylists);
    }

    @Override
    public ResponseEntity<PlaylistResponse> getById(int id) {
        Playlist playlist = repository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        PlaylistResponse response = mapper.forResponse().map(playlist, PlaylistResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreatePlaylist> add(CreatePlaylist playlist) {
        Playlist mappedPlaylist = mapper.forRequest().map(playlist, Playlist.class);
        repository.save(mappedPlaylist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @Override
    public ResponseEntity<PlaylistResponse> delete(int id) {
        Playlist playlist = repository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        repository.deleteById(id);
        PlaylistResponse response = mapper.forResponse().map(playlist, PlaylistResponse.class);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Override
    public ResponseEntity<UpdatePlaylist> update(UpdatePlaylist playlist) {
        Playlist updatePlaylist = repository.findById(playlist.getId())
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlist.getId()));
        Playlist mappedPlaylist = mapper.forRequest().map(playlist, Playlist.class);
        repository.save(mappedPlaylist);
        return ResponseEntity.ok(playlist);
    }
}