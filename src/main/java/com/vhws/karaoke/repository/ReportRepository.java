package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM report r WHERE r.house.houseId = :houseId ORDER BY r.dateOfReport ASC")
    List<Report> findByHouseAndDate(@Param("houseId") String houseId);
}
