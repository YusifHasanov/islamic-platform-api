package com.msys.esm.Repository;

import com.msys.esm.Model.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findAllByPlaylistId(String playlistId);
    List<Video> findVideosByPlaylistId(String playlistId, Pageable pageable);
    List<Video> findAllByPlaylistId(String playlistId,Sort sort);
}
