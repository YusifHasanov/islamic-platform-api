package com.msys.esm.dataAccess;

import com.msys.esm.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findVideosByPlaylistPlaylistId(String playlistId);
//    List<Video> findVideosByPlaylistId(String playlistId , Pageable pageable);
}
