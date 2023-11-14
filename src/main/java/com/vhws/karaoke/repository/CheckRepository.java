package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Check;
import jakarta.persistence.NoResultException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, String> {
    @Query("SELECT c FROM Check c WHERE c.house.houseId = :houseId AND c.taken = false")
    List<Check> findWhereNotTaken(@Param("houseId") String houseId);
}
