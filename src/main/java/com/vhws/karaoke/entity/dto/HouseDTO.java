package com.vhws.karaoke.entity.dto;

import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.Music;
import jakarta.persistence.*;

import java.util.List;

public class HouseDTO {
    private String houseId;
    private String houseName;
    private String cnpj;
    private String phone;
    private String address;
    private List<Check> checkList;
    private List<Music> previousSongs;
    private List<Music> nextSongs;

    public HouseDTO() {
    }

    public HouseDTO(String houseId, String houseName, String cnpj, String phone, String address, List<Check> checkList, List<Music> previousSongs, List<Music> nextSongs) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.cnpj = cnpj;
        this.phone = phone;
        this.address = address;
        this.checkList = checkList;
        this.previousSongs = previousSongs;
        this.nextSongs = nextSongs;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Check> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<Check> checkList) {
        this.checkList = checkList;
    }

    public List<Music> getPreviousSongs() {
        return previousSongs;
    }

    public void setPreviousSongs(List<Music> previousSongs) {
        this.previousSongs = previousSongs;
    }

    public List<Music> getNextSongs() {
        return nextSongs;
    }

    public void setNextSongs(List<Music> nextSongs) {
        this.nextSongs = nextSongs;
    }
}
