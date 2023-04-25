package com.msys.esm.Service.Abstracts;


import com.msys.esm.Core.DTO.Request.Create.CreatePlaylist;
import com.msys.esm.Core.DTO.Request.Update.UpdatePlaylist;
import com.msys.esm.Core.DTO.Response.PlaylistResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IPlaylistService {
    ResponseEntity<List<PlaylistResponse>> getAll();
    ResponseEntity<PlaylistResponse> getById(String id);
    ResponseEntity<CreatePlaylist> add(CreatePlaylist playlist);
    void updatePlaylists() throws IOException;
    ResponseEntity<PlaylistResponse> delete(String id);
    ResponseEntity<UpdatePlaylist> update(UpdatePlaylist  playlist,String id);
}
