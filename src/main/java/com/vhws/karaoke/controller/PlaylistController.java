package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.dto.PlaylistDTO;
import com.vhws.karaoke.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;

    @GetMapping("/showAllPlaylists")
    public ResponseEntity<List<PlaylistDTO>> showAllPlaylists(){
        List<PlaylistDTO> playlistDTOs = playlistService.showAllPlaylists();
        return new ResponseEntity<>(playlistDTOs, HttpStatus.OK);
    }

    @GetMapping("/showPlaylist/{playlistId}")
    public ResponseEntity<PlaylistDTO> showPlaylist(@PathVariable String playlistId){
        PlaylistDTO playlistDTO = playlistService.showPlaylist(playlistId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @PostMapping("/addPlaylist")
    public ResponseEntity<PlaylistDTO> addPlaylist(@RequestBody PlaylistDTO playlistDTO){
        playlistDTO = playlistService.addPlaylist(playlistDTO);
        return new ResponseEntity<>(playlistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/addToPlaylist/{playlistId}/{musicId}")
    public ResponseEntity<PlaylistDTO> addToPlaylist(@PathVariable String playlistId, @PathVariable String musicId){
        PlaylistDTO playlistDTO = playlistService.addToPlaylist(playlistId, musicId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @PutMapping("/changePlaylist/{playlistID}")
    public ResponseEntity<PlaylistDTO> changePlaylist(@RequestBody PlaylistDTO playlistDTO, @PathVariable String playlistId){
        playlistDTO = playlistService.changePlaylist(playlistDTO, playlistId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deletePlaylist/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String playlistId){
        playlistService.deletePlaylist(playlistId);
        return new ResponseEntity<>("Playlist deleted with success!", HttpStatus.OK);
    }
}
