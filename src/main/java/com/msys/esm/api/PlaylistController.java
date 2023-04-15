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
      PlaylistService service;

     @GetMapping
     public ResponseEntity<List<PlaylistResponse>> getAll() {
         return service.getAll();
     }

     @GetMapping("/{id}")
     public ResponseEntity<PlaylistResponse> getById(@PathVariable String id) {
         return service.getById(id);
     }

     @PostMapping
     public ResponseEntity<CreatePlaylist> add(@Valid @RequestBody CreatePlaylist playlist) {
         return service.add(playlist);
     }

     @PutMapping("/{id}")
     public ResponseEntity<UpdatePlaylist> update(@Valid @RequestBody UpdatePlaylist playlist,@PathVariable String id) {

         return service.update(playlist,id);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<PlaylistResponse> delete(@PathVariable String id) {
         return service.delete(id);
     }
 }

