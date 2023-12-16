package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.dto.MusicDTO;
import com.vhws.karaoke.entity.response.SongsOnListResponse;
import com.vhws.karaoke.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    MusicService musicService;

    @GetMapping("/showAllSongs")
    public ResponseEntity<List<MusicDTO>> showAllSongs(){
        List<MusicDTO> musicDTOs = musicService.showAllSongs();
        return new ResponseEntity<>(musicDTOs, HttpStatus.OK);
    }

    @GetMapping("/showSong/{musicId}")
    public ResponseEntity<MusicDTO> showSong(@PathVariable String musicId){
        MusicDTO musicDTO = musicService.showSong(musicId);
        return new ResponseEntity<>(musicDTO, HttpStatus.OK);
    }

    @GetMapping("/searchMusic/{search}")
    public ResponseEntity<List<MusicDTO>> searchMusic(@PathVariable String search){
        List<MusicDTO> songs = musicService.searchMusic(search);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/showPreviousSongs/{houseId}")
    public ResponseEntity<List<MusicDTO>> showPreviousSongs(@PathVariable String houseId){
        List<MusicDTO> songs = musicService.showPreviousSongs(houseId);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/showNextSongs/{houseId}")
    public ResponseEntity<List<SongsOnListResponse>> showNextSongs(@PathVariable String houseId){
        List<SongsOnListResponse> songs = musicService.showNextSongs(houseId); //nome da musica, o artista, nome do cliente, tempo de duração e id da musica;
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @PostMapping("/addMusic")
    public ResponseEntity<MusicDTO> addMusic(@RequestBody MusicDTO musicDTO){
        musicDTO = musicService.addMusic(musicDTO);
        return new ResponseEntity<>(musicDTO, HttpStatus.CREATED);
    }

    @PutMapping("/addToNextSong/{houseId}/{checkId}/{musicId}")
    public ResponseEntity<?> addToNextSong(@PathVariable String houseId, @PathVariable String checkId, @PathVariable String musicId){
        musicService.addToNextSong(houseId, checkId, musicId);
        return new ResponseEntity<>("Your song has been successfully added to the list", HttpStatus.OK);
    }

    @PutMapping("/addToPreviousSong/{houseId}/{checkId}")
    public ResponseEntity<List<MusicDTO>> addToPreviousSong(@PathVariable String houseId, @PathVariable String checkId){
        List<MusicDTO> musicDTOs = musicService.addToPreviousSong(houseId, checkId);
        return new ResponseEntity<>(musicDTOs, HttpStatus.OK);
    }

    @PutMapping("/changeMusic/{musicId}")
    public ResponseEntity<MusicDTO> changeMusic(@RequestBody MusicDTO musicDTO, @PathVariable String musicId){
        musicDTO = musicService.changeMusic(musicDTO, musicId);
        return new ResponseEntity<>(musicDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMusic/{musicId}")
    public ResponseEntity<?> deleteMusic(@PathVariable String musicId){
        musicService.deleteMusic(musicId);
        return new ResponseEntity<>("Music deleted with success!", HttpStatus.OK);
    }
}
