package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.HouseRequest;
import com.vhws.karaoke.entity.dto.CheckDTO;
import com.vhws.karaoke.entity.dto.HouseDTO;
import com.vhws.karaoke.entity.dto.MusicDTO;
import com.vhws.karaoke.entity.dto.PlaylistDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.model.Music;
import com.vhws.karaoke.entity.model.Playlist;
import com.vhws.karaoke.entity.response.CustomersIn;
import com.vhws.karaoke.repository.CheckRepository;
import com.vhws.karaoke.repository.HouseRepository;
import com.vhws.karaoke.repository.MusicRepository;
import com.vhws.karaoke.repository.PlaylistRepository;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralService {
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private PlaylistRepository playlistRepository;


    public List<HouseDTO> showAllHouses() {
        List<House> houseList = houseRepository.findAll();
        if (houseList.isEmpty())
            throw new ResourceNotFoundException("No houses were found!");
        List<HouseDTO> houseDTOs = new ArrayList<>();
        for (House house : houseList) {
            HouseDTO houseDTO = createHouseDTO(house);
            houseDTOs.add(houseDTO);
        }
        return houseDTOs;
    }

    public HouseDTO showHouse(String houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty())
            throw new ResourceNotFoundException("House not found!");
        House house = houseOptional.get();
        HouseDTO houseDTO = createHouseDTO(house);
        return houseDTO;
    }

    public List<CheckDTO> showAllChecks() {
        List<Check> checkList = checkRepository.findAll();
        if (checkList.isEmpty())
            throw new ResourceNotFoundException("No checks were found!");
        List<CheckDTO> checkDTOList = new ArrayList<>();
        for (Check check : checkList) {
            CheckDTO checkDTO = new CheckDTO(check.getCheckId(), check.getHouse(), check.getCostumerName(), check.getHouseName(), check.getCheckNumber(), check.isTaken(), check.isOpen(), check.getNextSong());
            checkDTOList.add(checkDTO);
        }
        return checkDTOList;
    }

    public CheckDTO showCheck(String checkId) {
        Optional<Check> checkOptional = checkRepository.findById(checkId);
        if (checkOptional.isEmpty())
            throw new ResourceNotFoundException("Check not found!");
        Check check = checkOptional.get();
        CheckDTO checkDTO = new CheckDTO(check.getCheckId(), check.getHouse(), check.getCostumerName(), check.getHouseName(), check.getCheckNumber(), check.isTaken(), check.isOpen(), check.getNextSong());
        return checkDTO;
    }

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

    public List<CustomersIn> showCustomersIn(String houseId){
       Optional<House> houseOptional = houseRepository.findById(houseId);
       if(houseOptional.isEmpty())
           throw new ResourceNotFoundException("No costumers found!");
       House house = houseOptional.get();
       List<Check> checkList = house.getCheckList();
       List<CustomersIn> costumersInList = new ArrayList<>();
       for(Check check:checkList){
           if(check.getCostumerName()==null){
               check.setCostumerName("Em aberto");
           }
           CustomersIn costumersIn = new CustomersIn(check.getCostumerName(), check.getCheckNumber(), check.getNextSong());
           costumersInList.add(costumersIn);
       }
       return costumersInList;
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

    public HouseDTO addHouse(HouseRequest houseRequest){
        House house = new House(houseRequest.getHouseName(),houseRequest.getCnpj(), houseRequest.getPhone(), houseRequest.getAddress());
        house = houseRepository.save(house);
        HouseDTO houseDTO = createHouseDTO(house);
        return houseDTO;
    }

    public CheckDTO addCheck(CheckDTO checkDTO, String houseId){
       Optional<House> houseOptional = houseRepository.findById(houseId);
       if(houseOptional.isEmpty())
           throw new ResourceNotFoundException("House not found!");
       House house = houseOptional.get();
       List<Check> checkList = house.getCheckList();
        for (Check checkOnList:checkList){
            if(checkOnList.getCheckNumber().equals(checkDTO.getCheckNumber())){
                throw new ResourceBadRequestException("This check already exist.");
            }
        }
       Check check = new Check(house, house.getHouseName(), checkDTO.getCheckNumber());
        check = checkRepository.save(check);
        checkList.add(check);
        house.setCheckList(checkList);
        houseRepository.save(house);
       return checkDTO;
    }

    public MusicDTO addMusic(MusicDTO musicDTO){
        Music music = new Music(musicDTO.getMusicId(),musicDTO.getTitle(),musicDTO.getMusicGenre(),musicDTO.getMusicTag(),
                musicDTO.getArtist(),musicDTO.getAlbum(),musicDTO.getLink(),musicDTO.getRunningTime());
        music = musicRepository.save(music);
        musicDTO.setMusicId(music.getMusicId());
        return musicDTO;
    }

    public PlaylistDTO addPlaylist(PlaylistDTO playlistDTO){
       Playlist playlist = new Playlist(playlistDTO.getPlayListId(),playlistDTO.getName(),playlistDTO.getDescription(),
               playlistDTO.getPicture(),playlistDTO.getSongs());
       playlist = playlistRepository.save(playlist);
       playlistDTO.setPlayListId(playlist.getPlayListId());
       return playlistDTO;
    }


    public CheckDTO checkInValidation(CheckDTO checkDTO, String houseId){
        List<Check> checks = checkRepository.findWhereNotTaken(houseId);
        if (checks.isEmpty()) {
            throw new ResourceNotFoundException("Check not found!");
        }
        Check check = checks.get(0);
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isEmpty())
            throw new ResourceNotFoundException("House not found!");
        House house = houseOptional.get();
        List<Check> checkList = house.getCheckList();
        for(Check checkFor:checkList) {
            if (check.equals(checkFor)) {
                check.setCostumerName(checkDTO.getCustomerName());
                check.setTaken(true);
                check.setOpen(true);
                checkRepository.save(check);
            }
        }
        checkDTO.setCheckId(check.getCheckId());
        checkDTO.setCheckNumber(check.getCheckNumber());
        checkDTO.setOpen(check.isOpen());
        checkDTO.setTaken(check.isTaken());
        return checkDTO;
    }

    public CheckDTO checkOut(String checkId){
        Optional<Check> checkOptional = checkRepository.findById(checkId);
        if(checkOptional.isEmpty())
            throw new ResourceNotFoundException("Check not found!");
        Check check = checkOptional.get();
        check.setOpen(false);
        checkRepository.save(check);
        CheckDTO checkDTO = createCheckDTO(check);
        return checkDTO;
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


    public HouseDTO changeHouse(HouseDTO houseDTO, String houseId) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("House not found!"));

        if (houseDTO.getAddress() != null && !houseDTO.getAddress().isEmpty()) {
            house.setAddress(houseDTO.getAddress());
        }

        if (houseDTO.getHouseName() != null && !houseDTO.getHouseName().isEmpty()) {
            house.setHouseName(houseDTO.getHouseName());
        }

        if (houseDTO.getCnpj() != null && !houseDTO.getCnpj().isEmpty()) {
            house.setCnpj(houseDTO.getCnpj());
        }

        if (houseDTO.getNextSongs() != null && !houseDTO.getNextSongs().isEmpty()) {
            house.setNextSongs(houseDTO.getNextSongs());
        }

        if (houseDTO.getCheckList() != null && !houseDTO.getCheckList().isEmpty()) {
            house.setCheckList(houseDTO.getCheckList());
        }

        if (houseDTO.getPhone() != null && !houseDTO.getPhone().isEmpty()) {
            house.setPhone(houseDTO.getPhone());
        }

        if (houseDTO.getPreviousSongs() != null && !houseDTO.getPreviousSongs().isEmpty()) {
            house.setPreviousSongs(houseDTO.getPreviousSongs());
        }

        houseRepository.save(house);

        HouseDTO houseDTOResponse = createHouseDTO(house);
        return houseDTOResponse;
    }

    public CheckDTO changeCheck(CheckDTO checkDTO, String checkId){
        Check check = checkRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException("Check not found!"));

        if (checkDTO.getCheckNumber() != null && checkDTO.getCheckNumber() != 0){
            check.setCheckNumber(checkDTO.getCheckNumber());
        }

        if (checkDTO.getHouseName() != null && !checkDTO.getHouseName().isEmpty()){
            check.setHouseName(check.getHouseName());
        }

        if (checkDTO.getNextSong() != null){
            check.setNextSong(checkDTO.getNextSong());
        }

        if (checkDTO.getCustomerName() != null && !checkDTO.getCustomerName().isEmpty()){
            check.setCostumerName(checkDTO.getCustomerName());
        }

        if (checkDTO.getHouse() != null){
            check.setHouse(check.getHouse());
        }
        if(checkDTO.isOpen() != check.isOpen()){
            check.setOpen(checkDTO.isOpen());
        }
        if(checkDTO.isTaken() != check.isTaken()){
            check.setTaken(checkDTO.isTaken());
        }

        checkRepository.save(check);

        CheckDTO checkDTOResponse = createCheckDTO(check);
        return checkDTOResponse;
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

    public void deleteHouse(String houseId){
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<Check> checkList = house.getCheckList();
        house.setCheckList(null);
        for(Check check:checkList){
            check.setHouse(null);
            checkRepository.delete(check);
        }
        house.setPreviousSongs(null);
        house.setNextSongs(null);

        houseRepository.delete(house);
    }

    public void deleteCheck(String checkId){
        Check check = checkRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException("Check not found!"));
        House house = check.getHouse();
        List<Check> checkList = house.getCheckList();
        checkList.remove(check);
        house.setCheckList(checkList);
        houseRepository.save(house);
        check.setHouse(null);
        checkRepository.delete(check);
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

    public void deletePlaylist(String playListId){
        Playlist playlist = playlistRepository.findById(playListId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found!"));
        playlist.setSongs(null);
        playlistRepository.delete(playlist);
    }

    //------------------------------------------------------------------------------------------------------------------
    private HouseDTO createHouseDTO(House house) {
        return new HouseDTO(
                house.getHouseId(),
                house.getHouseName(),
                house.getCnpj(),
                house.getPhone(),
                house.getAddress(),
                house.getCheckList(),
                house.getPreviousSongs(),
                house.getNextSongs()
        );
    }

    private CheckDTO createCheckDTO(Check check){
        return new CheckDTO(
                check.getCheckId(),
                check.getHouse(),
                check.getCostumerName(),
                check.getHouseName(),
                check.getCheckNumber(),
                check.isTaken(),
                check.isOpen(),
                check.getNextSong()
        );
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
