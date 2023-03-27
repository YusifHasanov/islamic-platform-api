package com.example.api.business.abstracts;


import com.example.api.core.dto.Request.Create.CreatePlaylist;
import com.example.api.core.dto.Request.Update.UpdatePlaylist;
import com.example.api.core.dto.Response.PlaylistResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlaylistService {
    ResponseEntity<List<PlaylistResponse>> getAll();
    ResponseEntity<PlaylistResponse> getById(int id);
    ResponseEntity<CreatePlaylist> add(CreatePlaylist playlist);
    ResponseEntity<PlaylistResponse> delete(int id);
    ResponseEntity<UpdatePlaylist> update(UpdatePlaylist  playlist);
}
