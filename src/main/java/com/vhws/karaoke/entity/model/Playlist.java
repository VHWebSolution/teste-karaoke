package com.vhws.karaoke.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Playlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "play_list_id")
    private String playListId;
    private String name;
    private String description;
    @Column(length = 50000000)
    private byte[] picture;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "song_on_playlist", joinColumns = {
            @JoinColumn(name = "play_list_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "music_id")
    })
    private List<Music> songs;
}
