package com.msys.esm.api;
import com.msys.esm.business.concretes.PlaylistService;
import com.msys.esm.core.dto.Request.Create.CreatePlaylist;
import com.msys.esm.core.dto.Request.Update.UpdatePlaylist;
import com.msys.esm.core.dto.Response.PlaylistResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

 @RestController
 @RequestMapping("/api/playlists")
 @RequiredArgsConstructor
 @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
 public class PlaylistController {
      PlaylistService playlistService;

     @GetMapping
     public ResponseEntity<List<PlaylistResponse>> getAll() {
         return playlistService.getAll();
     }

     @GetMapping("/{id}")
     public ResponseEntity<PlaylistResponse> getById(@PathVariable int id) {
         return playlistService.getById(id);
     }

     @PostMapping
     public ResponseEntity<CreatePlaylist> add(@Valid @RequestBody CreatePlaylist playlist) {
         return playlistService.add(playlist);
     }

     @PutMapping("/{id}")
     public ResponseEntity<UpdatePlaylist> update(@Valid @RequestBody UpdatePlaylist playlist,@PathVariable int id) {

         return playlistService.update(playlist,id);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<PlaylistResponse> delete(@PathVariable int id) {
         return playlistService.delete(id);
     }
 }

