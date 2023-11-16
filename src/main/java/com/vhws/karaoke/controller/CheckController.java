package com.vhws.karaoke.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vhws.karaoke.entity.dto.CheckDTO;
import com.vhws.karaoke.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/check")
public class CheckController {
    @Autowired
    CheckService checkService;

    @GetMapping("/QRCodeCheckIn") //mudarURL
    public ResponseEntity<byte[]> generateQRCode() {
        String redirectToLogin = "https://vhwebsolutions.com.br/";
        byte[] qrcodeBytes = generateQRCodeAsBytes(redirectToLogin,200,200);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrcodeBytes.length);
        return new ResponseEntity<>(qrcodeBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/showAllChecks")
    public ResponseEntity<List<CheckDTO>> showAllChecks(){
        List<CheckDTO> checkDTOs = checkService.showAllChecks();
        return new ResponseEntity<>(checkDTOs, HttpStatus.OK);
    }

    @GetMapping("/showCheck/{checkId}")
    public ResponseEntity<CheckDTO> showCheck(@PathVariable String checkId){
        CheckDTO checkDTO = checkService.showCheck(checkId);
        return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PostMapping("/addCheck/{houseId}")
    public ResponseEntity<CheckDTO> addCheck(@RequestBody CheckDTO checkDTO, @PathVariable String houseId){
        checkDTO = checkService.addCheck(checkDTO, houseId);
        return new ResponseEntity<>(checkDTO, HttpStatus.CREATED);
    }

    @PutMapping("/checkInValidation/{houseId}")
    public ResponseEntity<CheckDTO> checkInValidation(@RequestBody CheckDTO checkDTO, @PathVariable String houseId){
        checkDTO = checkService.checkInValidation(checkDTO, houseId);
        return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/checkOut/{checkId}")
    public ResponseEntity<CheckDTO> checkOut(@PathVariable String checkId){
        CheckDTO checkDTO = checkService.checkOut(checkId);
        return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/changeCheck/{checkId}")
    public ResponseEntity<CheckDTO> changeCheck(@RequestBody CheckDTO checkDTO, @PathVariable String checkId){
        checkDTO = checkService.changeCheck(checkDTO, checkId);
        return new ResponseEntity<>(checkDTO, HttpStatus.OK);
    }

    @PutMapping("/resetChecks/{houseId}")
    public ResponseEntity<List<CheckDTO>> resetChecks(@PathVariable String houseId){
        List<CheckDTO> checks = checkService.resetChecks(houseId);
        return new ResponseEntity<>(checks, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCheck/{checkId}")
    public ResponseEntity<?> deleteCheck(@PathVariable String checkId){
        checkService.deleteCheck(checkId);
        return new ResponseEntity<>("Check deleted with success!", HttpStatus.OK);
    }

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
