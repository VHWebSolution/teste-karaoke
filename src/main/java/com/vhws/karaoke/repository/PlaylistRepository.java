package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
