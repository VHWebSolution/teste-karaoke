package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.dto.MusicDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.model.Music;
import com.vhws.karaoke.entity.model.Playlist;
import com.vhws.karaoke.entity.model.SongsOnList;
import com.vhws.karaoke.entity.response.SongsOnListResponse;
import com.vhws.karaoke.repository.CheckRepository;
import com.vhws.karaoke.repository.HouseRepository;
import com.vhws.karaoke.repository.MusicRepository;
import com.vhws.karaoke.repository.PlaylistRepository;
import com.vhws.karaoke.repository.SongsOnListRepository;

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
    @Autowired
    private SongsOnListRepository songsOnListRepository;

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

    public List<SongsOnListResponse> showPreviousSongs(String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<SongsOnList> previousSongs = house.getPreviousSongs();
        List<SongsOnListResponse> previousSongsDTO = new ArrayList<>();
        for(SongsOnList m:previousSongs){
            SongsOnListResponse song = createSongsOnListResponse(m);
            previousSongsDTO.add(song);
        }
        Collections.reverse(previousSongsDTO);
        return previousSongsDTO;
    }

    public List<SongsOnListResponse> showNextSongs(String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<SongsOnListResponse> musicDTOList = new ArrayList<>();
        for(SongsOnList m:house.getNextSongs()){
            SongsOnListResponse musicDTO = createSongsOnListResponse(m);
            musicDTOList.add(musicDTO);
        }
        Collections.reverse(musicDTOList);
        return musicDTOList;
    }

    public MusicDTO addMusic(MusicDTO musicDTO){
        Music music = new Music(musicDTO.getMusicId(),musicDTO.getTitle(),musicDTO.getMusicGenre(),musicDTO.getMusicTag(),
                musicDTO.getArtist(),musicDTO.getAlbum(),musicDTO.getLink(),musicDTO.getRunningTime());
        music = musicRepository.save(music);
        musicDTO.setMusicId(music.getMusicId());
        return musicDTO;
    }

    public List<SongsOnListResponse> addToNextSong(String houseId, String checkId, String musicId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        Check check = checkRepository.findById(checkId).orElseThrow(() -> new ResourceNotFoundException("Check not found!"));
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new ResourceNotFoundException("Music not found!"));
        if(check.getNextSong()!=null){
            throw new ResourceBadRequestException("This check is already on the list!");
        }
        SongsOnList song = createSongsOnList(music, check);
        song = songsOnListRepository.save(song);

        house.getNextSongs().add(song);
        houseRepository.save(house);

        check.setNextSong(music);
        checkRepository.save(check);

        List<SongsOnListResponse> nextSongs = new ArrayList<>();
        for(SongsOnList s:house.getNextSongs()){
            SongsOnListResponse  response = createSongsOnListResponse(s);
            nextSongs.add(response);  
        } 
        
        return nextSongs;
    }

    public List<SongsOnListResponse> addToPreviousSong(String houseId, String checkId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        Check check = checkRepository.findById(checkId).orElseThrow(() -> new ResourceNotFoundException("Check not found!"));

        if(check.getNextSong()==null){
            throw new ResourceBadRequestException("This check has no songs on the list!");
        }

        SongsOnList song = songsOnListRepository.findByCheckNumber(check.getCheckNumber());

        house.getNextSongs().remove(song);
        house.getPreviousSongs().add(song);
        houseRepository.save(house);

        check.setNextSong(null);
        checkRepository.save(check);

        List<SongsOnListResponse> previousSongs = new ArrayList<>();

        for(SongsOnList s:house.getPreviousSongs()){
            SongsOnListResponse response = createSongsOnListResponse(s);
            previousSongs.add(response);
        }

        return previousSongs;
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

        if (musicDTO.getRunningTime() != 0){
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

    private SongsOnListResponse createSongsOnListResponse(SongsOnList music){
        return new SongsOnListResponse(
                music.getMusicId(),
                music.getTitle(),
                music.getMusicGenre(),
                music.getArtist(),
                music.getLink(),
                music.getRunningTime(),
                music.getCustomerName()
        );
    }

    private SongsOnList createSongsOnList(Music music, Check check){
        return new SongsOnList(
                music.getMusicId(),
                music.getTitle(),
                music.getMusicGenre(),
                music.getArtist(),
                music.getLink(),
                music.getRunningTime(),
                check.getCustomerName(),
                check.getCheckNumber()
        );
    }
}
