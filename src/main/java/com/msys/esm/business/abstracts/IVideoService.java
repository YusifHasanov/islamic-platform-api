package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateVideo;
import com.msys.esm.core.dto.Request.Update.UpdateVideo;
import com.msys.esm.core.dto.Response.VideoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVideoService {
    ResponseEntity<List<VideoResponse>> getAll();
    ResponseEntity<VideoResponse> getById(String id);
    ResponseEntity<CreateVideo> add(CreateVideo video);
    ResponseEntity<VideoResponse> delete(String id);
    ResponseEntity<UpdateVideo> update(UpdateVideo video,String id);
    ResponseEntity<List<VideoResponse>> getByPlaylistId(String playlistId);
    void addOrUpdateVideos();
    //get videos with pagable
//    ResponseEntity<List<VideoResponse>> getByPlaylistIdAndPageable(String playlistId, Pageable pageable);
}
