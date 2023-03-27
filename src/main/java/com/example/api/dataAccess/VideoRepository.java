package com.example.api.dataAccess;

import com.example.api.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findVideosByPlaylistPlaylistId(String playlistId);
}
