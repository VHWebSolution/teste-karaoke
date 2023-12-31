package com.vhws.karaoke.entity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "music_id")
    private String musicId;
    private String title;
    private String musicGenre;
    private String musicTag;
    private String artist;
    private String album;
    private String link;
    private int runningTime;

    public Music() {
    }

    public Music(String musicId, String title, String musicGenre, String musicTag, String artist, String album, String link, int runningTime) {
        this.musicId = musicId;
        this.title = title;
        this.musicGenre = musicGenre;
        this.musicTag = musicTag;
        this.artist = artist;
        this.album = album;
        this.link = link;
        this.runningTime = runningTime;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public String getMusicTag() {
        return musicTag;
    }

    public void setMusicTag(String musicTag) {
        this.musicTag = musicTag;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }
}
