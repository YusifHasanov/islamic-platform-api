package com.msys.esm.Repository;

import com.msys.esm.Model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
