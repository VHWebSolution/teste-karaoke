package com.vhws.karaoke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vhws.karaoke.entity.model.SongsOnList;

public interface SongsOnListRepository extends JpaRepository<SongsOnList, String>{
    
    @Query("SELECT s FROM SongsOnList s WHERE s.checkNumber = :checkNumber")
    SongsOnList findByCheckNumber(@Param("checkNumber") int checkNumber);
}
