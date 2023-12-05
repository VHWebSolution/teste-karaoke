package com.vhws.karaoke.entity.dto;

import java.util.List;

import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.Music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HouseDTO {
    private String houseId;
    private String houseName;
    private String cnpj;
    private String phone;
    private String address;
    private List<Check> checkList;
    private List<Music> previousSongs;
    private List<Music> nextSongs;
    private String picture;
}
