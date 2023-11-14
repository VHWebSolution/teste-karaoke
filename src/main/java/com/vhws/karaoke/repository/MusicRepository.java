package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, String> {
    @Query("SELECT m FROM Music m " +
            "WHERE " +
            "  (:criteria IS NULL " +
            "   OR m.title LIKE %:criteria% " +
            "   OR m.album LIKE %:criteria% " +
            "   OR m.artist LIKE %:criteria%" +
            "   OR m.musicTag LIKE %:criteria%" +
            "   OR m.musicGenre LIKE %:criteria%)")
    List<Music> findMusicsByCriteria(@Param("criteria") String criteria);
}
