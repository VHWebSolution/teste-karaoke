package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckRepository extends JpaRepository<Check, String> {
    @Query("SELECT c FROM Check c WHERE c.house.houseId = :houseId AND c.taken = false")
    Check findWhereNotTaken(@Param("houseId") String houseId);
}
