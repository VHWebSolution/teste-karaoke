package com.vhws.karaoke.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="House")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
            @JoinColumn(name = "songs_on_list_id")
    })
    private List<SongsOnList> previousSongs;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "next_song_list", joinColumns = {
            @JoinColumn(name = "house_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "songs_on_list_id")
    })
    private List<SongsOnList> nextSongs;
    @OneToMany(mappedBy = "house")
    @JsonIgnore
    private List<Report> reports;
    private int  validationNumber;
    @Column(length = 50000000)
    private byte[] picture;

    public House(String houseName, String cnpj, String phone, String address) {
        this.houseName = houseName;
        this.cnpj = cnpj;
        this.phone = phone;
        this.address = address;
    }


}
