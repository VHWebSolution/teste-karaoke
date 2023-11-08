package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, String> {
}
