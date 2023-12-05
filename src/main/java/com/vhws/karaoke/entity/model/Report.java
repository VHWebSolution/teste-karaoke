package com.vhws.karaoke.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private Long reportId;
    private int numberOfCustomers;
    private int numberOfSongs;
    @ManyToOne
    @JoinColumn(name="house_id")
    private House house;

    public Report(int numberOfCustomers, int numberOfSongs, House house) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfSongs = numberOfSongs;
        this.house = house;
    }
}
