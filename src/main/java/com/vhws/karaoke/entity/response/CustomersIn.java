package com.vhws.karaoke.entity.response;

import com.vhws.karaoke.entity.model.Music;

public class CustomersIn {
    private String costumersName;
    private Integer checkNumber;
    private Music nextSong;

    public CustomersIn() {
    }

    public CustomersIn(String costumersName, Integer checkNumber, Music nextSong) {
        this.costumersName = costumersName;
        this.checkNumber = checkNumber;
        this.nextSong = nextSong;
    }

    public String getCostumersName() {
        return costumersName;
    }

    public void setCostumersName(String costumersName) {
        this.costumersName = costumersName;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Music getNextSong() {
        return nextSong;
    }

    public void setNextSong(Music nextSong) {
        this.nextSong = nextSong;
    }
}
