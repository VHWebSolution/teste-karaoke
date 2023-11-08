package com.vhws.karaoke.entity.dto;

import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.model.Music;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class CheckDTO {
    private String checkId;
    private House house;
    private String customerName;
    private String houseName;
    private Integer checkNumber;
    private boolean taken;
    private boolean open;
    private Music nextSong;

    public CheckDTO() {
    }

    public CheckDTO(String checkId, House house, String customerName, String houseName, Integer checkNumber, boolean taken, boolean open, Music nextSong) {
        this.checkId = checkId;
        this.house = house;
        this.customerName = customerName;
        this.houseName = houseName;
        this.checkNumber = checkNumber;
        this.taken = taken;
        this.open = open;
        this.nextSong = nextSong;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
}
