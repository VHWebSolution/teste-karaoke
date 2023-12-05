package com.vhws.karaoke.entity.dto;

import com.vhws.karaoke.entity.model.House;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDTO {
    private Long reportId;
    private int numberOfCustomers;
    private int numberOfSongs;
    private House house;
}
