package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateVideo;
import com.msys.esm.core.dto.Request.Update.UpdateVideo;
import com.msys.esm.core.dto.Response.VideoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVideoService {
    ResponseEntity<List<VideoResponse>> getAll();
    ResponseEntity<VideoResponse> getById(int id);
    ResponseEntity<CreateVideo> add(CreateVideo video);
    ResponseEntity<VideoResponse> delete(int id);
    ResponseEntity<UpdateVideo> update(UpdateVideo video,int id);
    ResponseEntity<List<VideoResponse>> getByPlaylistId(String playlistId);
}
