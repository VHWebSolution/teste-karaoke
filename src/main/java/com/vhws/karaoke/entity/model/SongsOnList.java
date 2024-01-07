package com.vhws.karaoke.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="songs_on_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongsOnList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songs_on_list_id")
    private long songsOnListId;
    private String musicId;
    private String title;
    private String musicGenre;
    private String artist;
    private String link;
    private int runningTime;
    private String customerName;
    private String checkId;

    public SongsOnList(String musicId, String title, String musicGenre, String artist, String link, int runningTime, String customerName, String checkId) {
        this.musicId = musicId;
        this.title = title;
        this.musicGenre = musicGenre;
        this.artist = artist;
        this.link = link;
        this.runningTime = runningTime;
        this.customerName = customerName;
        this.checkId = checkId;
    }
}
