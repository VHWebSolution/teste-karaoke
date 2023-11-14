package com.vhws.karaoke.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vhws.karaoke.entity.HouseRequest;
import com.vhws.karaoke.entity.dto.CheckDTO;
import com.vhws.karaoke.entity.dto.HouseDTO;
import com.vhws.karaoke.entity.dto.MusicDTO;
import com.vhws.karaoke.entity.dto.PlaylistDTO;
import com.vhws.karaoke.entity.response.CustomersIn;
import com.vhws.karaoke.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/general")
public class GeneralController {
    @Autowired
    private GeneralService generalService;

    @GetMapping("/QRCodeCheckIn") //mudarURL
    public ResponseEntity<byte[]> generateQRCode() {
        String redirectToLogin = "https://vhwebsolutions.com.br/";
        byte[] qrcodeBytes = generateQRCodeAsBytes(redirectToLogin,200,200);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrcodeBytes.length);
        return new ResponseEntity<>(qrcodeBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/showAllHouses")
    public ResponseEntity<List<HouseDTO>> showAllHouses(){
    List<HouseDTO> houseDTOs = generalService.showAllHouses();
    return new ResponseEntity<>(houseDTOs, HttpStatus.OK);
    }

    @GetMapping("/showHouse/{houseId}")
    public ResponseEntity<HouseDTO> showHouse(@PathVariable String houseId){
    HouseDTO houseDTO = generalService.showHouse(houseId);
    return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    @GetMapping("/showAllChecks")
    public ResponseEntity<List<CheckDTO>> showAllChecks(){
    List<CheckDTO> checkDTOs = generalService.showAllChecks();
    return new ResponseEntity<>(checkDTOs, HttpStatus.OK);
    }

    @GetMapping("/showCheck/{checkId}")
    public ResponseEntity<CheckDTO> showCheck(@PathVariable String checkId){
    CheckDTO checkDTO = generalService.showCheck(checkId);
    return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @GetMapping("/showAllSongs")
    public ResponseEntity<List<MusicDTO>> showAllSongs(){
    List<MusicDTO> musicDTOs = generalService.showAllSongs();
    return new ResponseEntity<>(musicDTOs, HttpStatus.OK);
    }

    @GetMapping("/showSong/{musicId}")
    public ResponseEntity<MusicDTO> showSong(@PathVariable String musicId){
    MusicDTO musicDTO = generalService.showSong(musicId);
    return new ResponseEntity<>(musicDTO, HttpStatus.OK);
    }

    @GetMapping("/showAllPlaylists")
    public ResponseEntity<List<PlaylistDTO>> showAllPlaylists(){
    List<PlaylistDTO> playlistDTOs = generalService.showAllPlaylists();
    return new ResponseEntity<>(playlistDTOs, HttpStatus.OK);
    }

    @GetMapping("/showPlaylist/{playlistId}")
    public ResponseEntity<PlaylistDTO> showPlaylist(@PathVariable String playlistId){
    PlaylistDTO playlistDTO = generalService.showPlaylist(playlistId);
    return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @GetMapping("/showCustomersIn/{houseId}")
    public ResponseEntity<List<CustomersIn>> showCustomersIn(@PathVariable String houseId){
    List<CustomersIn> customersIn = generalService.showCustomersIn(houseId);
    return new ResponseEntity<>(customersIn, HttpStatus.OK);
    }

    @GetMapping("/searchMusic/{search}")
    public ResponseEntity<List<MusicDTO>> searchMusic(@PathVariable String search){
    List<MusicDTO> songs = generalService.searchMusic(search);
    return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @PostMapping("/addHouse")
    public ResponseEntity<HouseDTO> addHouse(@RequestBody HouseRequest houseRequest){
    HouseDTO houseDTO = generalService.addHouse(houseRequest);
    return new ResponseEntity<>(houseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addCheck/{houseId}")
    public ResponseEntity<CheckDTO> addCheck(@RequestBody CheckDTO checkDTO, @PathVariable String houseId){
    checkDTO = generalService.addCheck(checkDTO, houseId);
    return new ResponseEntity<>(checkDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addMusic")
    public ResponseEntity<MusicDTO> addMusic(@RequestBody MusicDTO musicDTO){
    musicDTO = generalService.addMusic(musicDTO);
    return new ResponseEntity<>(musicDTO, HttpStatus.CREATED);
    }

    @PostMapping("/addPlaylist")
    public ResponseEntity<PlaylistDTO> addPlaylist(@RequestBody PlaylistDTO playlistDTO){
    playlistDTO = generalService.addPlaylist(playlistDTO);
    return new ResponseEntity<>(playlistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/checkInValidation/{houseId}")
    public ResponseEntity<CheckDTO> checkInValidation(@RequestBody CheckDTO checkDTO, @PathVariable String houseId){
    checkDTO = generalService.checkInValidation(checkDTO, houseId);
    return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/checkOut/{checkId}")
    public ResponseEntity<CheckDTO> checkOut(@PathVariable String checkId){
    CheckDTO checkDTO = generalService.checkOut(checkId);
    return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/addToPlaylist/{playlistId}/{musicId}")
    public ResponseEntity<PlaylistDTO> addToPlaylist(@PathVariable String playlistId, @PathVariable String musicId){
    PlaylistDTO playlistDTO = generalService.addToPlaylist(playlistId, musicId);
    return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @PutMapping("/addToNextSong/{houseId}/{checkId}/{musicId}")
    public ResponseEntity<List<MusicDTO>> addToNextSong(@PathVariable String houseId, @PathVariable String checkId, @PathVariable String musicId){
    List<MusicDTO> musicDTOs = generalService.addToNextSong(houseId, checkId, musicId);
    return new ResponseEntity<>(musicDTOs, HttpStatus.OK);
    }

    @PutMapping("/addToPreviousSong/{houseId}/{checkId}")
    public ResponseEntity<List<MusicDTO>> addToPreviousSong(@PathVariable String houseId, @PathVariable String checkId){
    List<MusicDTO> musicDTOs = generalService.addToPreviousSong(houseId, checkId);
    return new ResponseEntity<>(musicDTOs, HttpStatus.OK);
    }

    @PutMapping("/changeHouse/{houseId}")
    public ResponseEntity<HouseDTO> changeHouse(@RequestBody HouseDTO houseDTO, @PathVariable String houseId){
    houseDTO = generalService.changeHouse(houseDTO, houseId);
    return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    @PutMapping("/changeCheck/{checkId}")
    public ResponseEntity<CheckDTO> changeCheck(@RequestBody CheckDTO checkDTO, @PathVariable String checkId){
    checkDTO = generalService.changeCheck(checkDTO, checkId);
    return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/changeMusic/{musicId}")
    public ResponseEntity<MusicDTO> changeMusic(@RequestBody MusicDTO musicDTO, @PathVariable String musicId){
    musicDTO = generalService.changeMusic(musicDTO, musicId);
    return new ResponseEntity<>(musicDTO, HttpStatus.OK);
    }

    @PutMapping("/changePlaylist/{playlistID}")
    public ResponseEntity<PlaylistDTO> changePlaylist(@RequestBody PlaylistDTO playlistDTO, @PathVariable String playlistId){
    playlistDTO = generalService.changePlaylist(playlistDTO, playlistId);
    return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteHouse/{houseId}")
    public ResponseEntity<?> deleteHouse(@PathVariable String houseId){
    generalService.deleteHouse(houseId);
    return new ResponseEntity<>("House deleted with success!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteCheck/{checkId}")
    public ResponseEntity<?> deleteCheck(@PathVariable String checkId){
    generalService.deleteCheck(checkId);
    return new ResponseEntity<>("Check deleted with success!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteMusic/{musicId}")
    public ResponseEntity<?> deleteMusic(@PathVariable String musicId){
    generalService.deleteMusic(musicId);
    return new ResponseEntity<>("Music deleted with success!", HttpStatus.OK);
    }

    @DeleteMapping("/deletePlaylist/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String playlistId){
    generalService.deletePlaylist(playlistId);
    return new ResponseEntity<>("Playlist deleted with success!", HttpStatus.OK);
    }

//-----------------------------------------------------------------------------------

    public byte[] generateQRCodeAsBytes(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, matrixToImageConfig);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
