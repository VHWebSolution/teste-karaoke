package com.vhws.karaoke.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="House")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "house_id")
    private String houseId;
    private String houseName;
    private String cnpj;
    private String phone;
    private String address;
    @OneToMany(mappedBy = "house")
    @JsonIgnore
    private List<Check> checkList;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "previous_song_list", joinColumns = {
            @JoinColumn(name = "house_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "music_id")
    })
    private List<Music> previousSongs;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "next_song_list", joinColumns = {
            @JoinColumn(name = "house_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "music_id")
    })
    private List<Music> nextSongs;

    public House() {
    }

    public House(String houseId, String houseName, String cnpj, String phone, String address, List<Check> checkList, List<Music> previousSongs, List<Music> nextSongs) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.cnpj = cnpj;
        this.phone = phone;
        this.address = address;
        this.checkList = checkList;
        this.previousSongs = previousSongs;
        this.nextSongs = nextSongs;
    }

    public House(String houseName, String cnpj, String phone, String address) {
        this.houseName = houseName;
        this.cnpj = cnpj;
        this.phone = phone;
        this.address = address;
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
