package com.vhws.karaoke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vhws.karaoke.entity.model.Check;

public interface CheckRepository extends JpaRepository<Check, String> {
    @Query("SELECT c FROM Check c WHERE c.house.houseId = :houseId AND c.taken = false")
    List<Check> findWhereNotTaken(@Param("houseId") String houseId);

    @Query("SELECT c FROM Check c WHERE c.music.musicId = :musicId")
    Check findByMusicId(@Param("musicId") String musicId);
}
