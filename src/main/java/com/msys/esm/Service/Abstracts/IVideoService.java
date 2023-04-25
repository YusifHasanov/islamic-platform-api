package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateVideo;
import com.msys.esm.Core.DTO.Request.Update.UpdateVideo;
import com.msys.esm.Core.DTO.Response.VideoResponse;
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
    ResponseEntity<List<VideoResponse>> getSortedVideosBySpecificField(String fieldName, String sortOrder);

    ResponseEntity<List<VideoResponse>> getSortedPlayListVideosBySpecificField(String fieldName, String playListId, String sortOrder);

    ResponseEntity<?> getByPlaylistIdAndPagination(String playlistId, int page, int size);
}
