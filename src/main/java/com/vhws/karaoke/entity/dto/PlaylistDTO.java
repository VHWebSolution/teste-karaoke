package com.vhws.karaoke.entity.dto;

import java.util.List;

import com.vhws.karaoke.entity.model.Music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistDTO {
    private String playListId;
    private String name;
    private String description;
    private String picture;
    private List<Music> songs;
}
