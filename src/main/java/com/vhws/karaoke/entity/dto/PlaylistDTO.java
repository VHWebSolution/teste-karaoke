package com.vhws.karaoke.entity.dto;

import com.vhws.karaoke.entity.model.Music;
import jakarta.persistence.*;

import java.util.List;

public class PlaylistDTO {
    private String playListId;
    private String name;
    private String description;
    private byte[] picture;
    private List<Music> songs;

    public PlaylistDTO() {
    }

    public PlaylistDTO(String playListId, String name, String description, byte[] picture, List<Music> songs) {
        this.playListId = playListId;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.songs = songs;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public List<Music> getSongs() {
        return songs;
    }

    public void setSongs(List<Music> songs) {
        this.songs = songs;
    }
}
