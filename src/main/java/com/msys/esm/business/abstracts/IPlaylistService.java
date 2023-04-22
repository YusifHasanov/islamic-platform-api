package com.msys.esm.business.abstracts;


import com.msys.esm.core.dto.Request.Create.CreatePlaylist;
import com.msys.esm.core.dto.Request.Update.UpdatePlaylist;
import com.msys.esm.core.dto.Response.PlaylistResponse;
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
