package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.dto.PlaylistDTO;
import com.vhws.karaoke.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value = "/addPlaylist", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlaylistDTO> addPlaylist(@ModelAttribute PlaylistDTO playlistDTO,
                                                   @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        playlistDTO = playlistService.addPlaylist(playlistDTO, file);
        return new ResponseEntity<>(playlistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/addToPlaylist/{playlistId}/{musicId}")
    public ResponseEntity<PlaylistDTO> addToPlaylist(@PathVariable String playlistId, @PathVariable String musicId){
        PlaylistDTO playlistDTO = playlistService.addToPlaylist(playlistId, musicId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/changePlaylist/{playlistID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlaylistDTO> changePlaylist(@ModelAttribute PlaylistDTO playlistDTO,
                                                      @PathVariable String playlistId,
                                                      @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        playlistDTO = playlistService.changePlaylist(playlistDTO, playlistId, file);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deletePlaylist/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String playlistId){
        playlistService.deletePlaylist(playlistId);
        return new ResponseEntity<>("Playlist deleted with success!", HttpStatus.OK);
    }
}
