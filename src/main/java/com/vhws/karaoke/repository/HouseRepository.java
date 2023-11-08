package com.vhws.karaoke.repository;

import com.vhws.karaoke.entity.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, String> {
}
