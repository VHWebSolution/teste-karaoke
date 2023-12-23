package com.vhws.karaoke.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongsOnList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "song_on_list_id")
    private String songOnListId;

    private String musicId;
    
    private String title;
    
    private String musicGenre;
    
    private String artist;
    
    private String link;
    
    private int runningTime;
    
    private String customerName;

    private int checkNumber;

    public SongsOnList(String musicId, String title, String musicGenre, String artist, String link, int runningTime,
            String customerName, int checkNumber) {
        this.musicId = musicId;
        this.title = title;
        this.musicGenre = musicGenre;
        this.artist = artist;
        this.link = link;
        this.runningTime = runningTime;
        this.customerName = customerName;
        this.checkNumber = checkNumber;
    }
    
}
