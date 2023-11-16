package com.vhws.karaoke.entity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "checks")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "check_id")
    private String checkId;
    private String customer;
    private String houseName;
    private Integer checkNumber;
    private boolean taken;
    private boolean open;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    @OneToOne
    @JoinColumn(name="music_id")
    private Music nextSong;

    public Check() {
    }

    public Check(String checkId, House house, String customer, String houseName, Integer checkNumber, boolean taken, boolean open, Music nextSong) {
        this.checkId = checkId;
        this.house = house;
        this.customer = customer;
        this.houseName = houseName;
        this.checkNumber = checkNumber;
        this.taken = taken;
        this.open = open;
        this.nextSong = nextSong;
    }

    public Check(House house, String houseName, Integer checkNumber) {
        this.house=house;
        this.houseName=houseName;
        this.checkNumber=checkNumber;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCustomerName() {
        return customer;
    }

    public void setCustomerName(String customer) {
        this.customer = customer;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Music getNextSong() {
        return nextSong;
    }

    public void setNextSong(Music nextSong) {
        this.nextSong = nextSong;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
