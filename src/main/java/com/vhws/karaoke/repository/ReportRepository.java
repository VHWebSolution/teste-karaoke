package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
