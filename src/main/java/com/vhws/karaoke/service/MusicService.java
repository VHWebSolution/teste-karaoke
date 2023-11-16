package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.dto.MusicDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.model.Music;
import com.vhws.karaoke.entity.model.Playlist;
import com.vhws.karaoke.repository.CheckRepository;
import com.vhws.karaoke.repository.HouseRepository;
import com.vhws.karaoke.repository.MusicRepository;
import com.vhws.karaoke.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public List<MusicDTO> showAllSongs() {
        List<Music> musicList = musicRepository.findAll();
        if (musicList.isEmpty())
            throw new ResourceNotFoundException("No musics were found!");
        List<MusicDTO> musicDTOs = new ArrayList<>();
        for (Music music : musicList) {
            MusicDTO musicDTO = new MusicDTO(music.getMusicId(), music.getTitle(), music.getMusicGenre(), music.getMusicTag(),
                    music.getArtist(), music.getAlbum(), music.getLink(), music.getRunningTime());
            musicDTOs.add(musicDTO);
        }
        return musicDTOs;
    }

    public MusicDTO showSong(String musicId) {
        Optional<Music> musicOptional = musicRepository.findById(musicId);
        if (musicOptional.isEmpty())
            throw new ResourceNotFoundException("Music not found!");
        Music music = musicOptional.get();
        MusicDTO musicDTO = new MusicDTO(music.getMusicId(), music.getTitle(), music.getMusicGenre(), music.getMusicTag(),
                music.getArtist(), music.getAlbum(), music.getLink(), music.getRunningTime());
        return musicDTO;
    }

    public List<MusicDTO> searchMusic(String search){
        List<Music> songList = musicRepository.findMusicsByCriteria(search);
        if(songList.isEmpty())
            throw new ResourceNotFoundException("No song were found!");
        List<MusicDTO> songsDTOlist = new ArrayList<>();
        for(Music song : songList){
            MusicDTO songDTO = createMusicDTO(song);
            songsDTOlist.add(songDTO);
        }
        return songsDTOlist;
    }

    public List<MusicDTO> showPreviousSongs(String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<Music> previousSongs = house.getPreviousSongs();
        List<MusicDTO> previousSongsDTO = new ArrayList<>();
        for(Music m:previousSongs){
            MusicDTO song = createMusicDTO(m);
            previousSongsDTO.add(song);
        }
        Collections.reverse(previousSongsDTO);
        return previousSongsDTO;
    }

    public List<MusicDTO> showNextSongs(String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<Music> nextSongs = house.getNextSongs();
        List<MusicDTO> nextSongsDTO = new ArrayList<>();
        for(Music m:nextSongs){
            MusicDTO song = createMusicDTO(m);
            nextSongsDTO.add(song);
        }
        Collections.reverse(nextSongsDTO);
        return nextSongsDTO;
    }

    public MusicDTO addMusic(MusicDTO musicDTO){
        Music music = new Music(musicDTO.getMusicId(),musicDTO.getTitle(),musicDTO.getMusicGenre(),musicDTO.getMusicTag(),
                musicDTO.getArtist(),musicDTO.getAlbum(),musicDTO.getLink(),musicDTO.getRunningTime());
        music = musicRepository.save(music);
        musicDTO.setMusicId(music.getMusicId());
        return musicDTO;
    }

    public List<MusicDTO> addToNextSong(String houseId, String checkId, String musicId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        Check check = checkRepository.findById(checkId).orElseThrow(() -> new ResourceNotFoundException("Check not found!"));
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new ResourceNotFoundException("Music not found!"));
        if(check.getNextSong()!=null){
            throw new ResourceBadRequestException("This check is already on the list!");
        }
        List<Music> nextSongs = house.getNextSongs();
        nextSongs.add(music);
        house.setNextSongs(nextSongs);
        check.setNextSong(music);
        houseRepository.save(house);
        checkRepository.save(check);
        List<MusicDTO> musicDTOList = new ArrayList<>();
        for(Music musicFor:nextSongs){
            MusicDTO musicDTO = createMusicDTO(musicFor);
            musicDTOList.add(musicDTO);
        }
        return musicDTOList;
    }

    public List<MusicDTO> addToPreviousSong(String houseId, String checkId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        Check check = checkRepository.findById(checkId).orElseThrow(() -> new ResourceNotFoundException("Check not found!"));
        if(check.getNextSong()==null){
            throw new ResourceBadRequestException("This check has no songs on the list!");
        }
        List<Music> nextSongs = house.getNextSongs();
        nextSongs.remove(check.getNextSong());
        List<Music> previousSongs = house.getPreviousSongs();
        previousSongs.add(check.getNextSong());
        house.setPreviousSongs(previousSongs);
        house.setNextSongs(nextSongs);
        check.setNextSong(null);
        houseRepository.save(house);
        checkRepository.save(check);
        List<MusicDTO> musicDTOList = new ArrayList<>();
        for(Music song:previousSongs){
            MusicDTO musicDTO = createMusicDTO(song);
            musicDTOList.add(musicDTO);
        }
        return musicDTOList;
    }

    public MusicDTO changeMusic(MusicDTO musicDTO, String musicId){
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Music not found!"));

        if (musicDTO.getAlbum() != null && !musicDTO.getAlbum().isEmpty()){
            music.setAlbum(musicDTO.getAlbum());
        }

        if (musicDTO.getMusicGenre() != null && !musicDTO.getMusicGenre().isEmpty()){
            music.setMusicGenre(musicDTO.getMusicGenre());
        }

        if (musicDTO.getMusicTag() != null && !musicDTO.getMusicTag().isEmpty()){
            music.setMusicTag(musicDTO.getMusicTag());
        }

        if (musicDTO.getLink() != null && !musicDTO.getLink().isEmpty()){
            music.setLink(musicDTO.getLink());
        }

        if (musicDTO.getArtist() != null && !musicDTO.getArtist().isEmpty()){
            music.setArtist(musicDTO.getArtist());
        }

        if (musicDTO.getAlbum() != null && !musicDTO.getAlbum().isEmpty()){
            music.setAlbum(musicDTO.getAlbum());
        }

        if (musicDTO.getRunningTime() != null && !musicDTO.getRunningTime().isEmpty()){
            music.setRunningTime(musicDTO.getRunningTime());
        }

        if (musicDTO.getTitle() != null && !musicDTO.getTitle().isEmpty()){
            music.setTitle(musicDTO.getTitle());
        }

        musicRepository.save(music);

        MusicDTO musicDTOResponse = createMusicDTO(music);
        return musicDTOResponse;
    }

    public void deleteMusic(String musicId){
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Music not found!"));
        List<House> houseList = houseRepository.findAll();
        for(House house:houseList){
            List<Music> nextSongs = house.getNextSongs();
            nextSongs.remove(music);
            List<Music> previousSongs = house.getPreviousSongs();
            previousSongs.remove(music);
            house.setNextSongs(nextSongs);
            house.setPreviousSongs(previousSongs);
            houseRepository.save(house);
        }
        List<Playlist> playlistList = playlistRepository.findAll();
        for(Playlist playlist:playlistList){
            List<Music> songs = playlist.getSongs();
            songs.remove(music);
            playlist.setSongs(songs);
            playlistRepository.save(playlist);
        }
        musicRepository.delete(music);
    }

    private MusicDTO createMusicDTO(Music music){
        return new MusicDTO(
                music.getMusicId(),
                music.getTitle(),
                music.getMusicGenre(),
                music.getMusicTag(),
                music.getArtist(),
                music.getAlbum(),
                music.getLink(),
                music.getRunningTime()
        );
    }
}