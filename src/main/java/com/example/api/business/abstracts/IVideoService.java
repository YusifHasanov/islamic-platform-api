package com.example.api.business.abstracts;

import com.example.api.core.dto.Request.Create.CreateVideo;
import com.example.api.core.dto.Request.Update.UpdateVideo;
import com.example.api.core.dto.Response.VideoResponse;
import com.example.api.entities.Video;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVideoService {
    ResponseEntity<List<VideoResponse>> getAll();
    ResponseEntity<VideoResponse> getById(int id);
    ResponseEntity<CreateVideo> add(CreateVideo video);
    ResponseEntity<VideoResponse> delete(int id);
    ResponseEntity<UpdateVideo> update(UpdateVideo video);
}
