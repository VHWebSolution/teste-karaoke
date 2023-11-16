package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.dto.PlaylistDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Music;
import com.vhws.karaoke.entity.model.Playlist;
import com.vhws.karaoke.repository.MusicRepository;
import com.vhws.karaoke.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private MusicRepository musicRepository;

    public List<PlaylistDTO> showAllPlaylists() {
        List<Playlist> playlistList = playlistRepository.findAll();
        if (playlistList.isEmpty())
            throw new ResourceNotFoundException("No playlists were found!");
        List<PlaylistDTO> playlistDTOs = new ArrayList<>();
        for (Playlist playlist : playlistList) {
            PlaylistDTO playlistDTO = new PlaylistDTO(playlist.getPlayListId(), playlist.getName(), playlist.getDescription(), playlist.getPicture(), playlist.getSongs());
            playlistDTOs.add(playlistDTO);
        }
        return playlistDTOs;
    }

    public PlaylistDTO showPlaylist(String playlistId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if(playlistOptional.isEmpty())
            throw new ResourceNotFoundException("Playlist not found!");
        Playlist playlist = playlistOptional.get();
        PlaylistDTO playlistDTO = new PlaylistDTO(playlist.getPlayListId(), playlist.getName(), playlist.getDescription(), playlist.getPicture(), playlist.getSongs());
        return playlistDTO;
    }

    public PlaylistDTO addPlaylist(PlaylistDTO playlistDTO){
        Playlist playlist = new Playlist(playlistDTO.getPlayListId(),playlistDTO.getName(),playlistDTO.getDescription(),
                playlistDTO.getPicture(),playlistDTO.getSongs());
        playlist = playlistRepository.save(playlist);
        playlistDTO.setPlayListId(playlist.getPlayListId());
        return playlistDTO;
    }

    public PlaylistDTO addToPlaylist(String playlistId, String musicId){
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if(playlistOptional.isEmpty())
            throw new ResourceNotFoundException("Playlist not found!");
        Optional<Music> musicOptional = musicRepository.findById(musicId);
        if(musicOptional.isEmpty())
            throw new ResourceNotFoundException("Music not found!");
        Playlist playlist = playlistOptional.get();
        Music music = musicOptional.get();
        List<Music> musicList = playlist.getSongs();
        musicList.add(music);
        playlist.setSongs(musicList);
        playlistRepository.save(playlist);
        PlaylistDTO playlistDTO = new PlaylistDTO(playlist.getPlayListId(), playlist.getName(), playlist.getDescription(), playlist.getPicture(), playlist.getSongs());
        return playlistDTO;
    }

    public PlaylistDTO changePlaylist(PlaylistDTO playlistDTO, String playlistId){
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found!"));
        if (playlistDTO.getName() != null && !playlistDTO.getName().isEmpty()){
            playlist.setName(playlistDTO.getName());
        }

        if (playlistDTO.getDescription() != null && !playlistDTO.getDescription().isEmpty()){
            playlist.setDescription(playlistDTO.getDescription());
        }

        if (playlistDTO.getPicture() != null){
            playlist.setPicture(playlistDTO.getPicture());
        }

        if (playlistDTO.getSongs() != null && !playlistDTO.getSongs().isEmpty()){
            playlist.setSongs(playlistDTO.getSongs());
        }

        playlistRepository.save(playlist);

        PlaylistDTO playlistDTOResponse = createPlaylistDTO(playlist);
        return playlistDTOResponse;
    }

    public void deletePlaylist(String playListId){
        Playlist playlist = playlistRepository.findById(playListId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found!"));
        playlist.setSongs(null);
        playlistRepository.delete(playlist);
    }

    private PlaylistDTO createPlaylistDTO(Playlist playlist){
        return new PlaylistDTO(
                playlist.getPlayListId(),
                playlist.getName(),
                playlist.getDescription(),
                playlist.getPicture(),
                playlist.getSongs()
        );
    }
}
