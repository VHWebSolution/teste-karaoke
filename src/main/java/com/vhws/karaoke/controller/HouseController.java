package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.dto.ReportDTO;
import com.vhws.karaoke.entity.request.HouseRequest;
import com.vhws.karaoke.entity.dto.HouseDTO;
import com.vhws.karaoke.entity.response.CustomersIn;
import com.vhws.karaoke.entity.response.ReportResponse;
import com.vhws.karaoke.service.HouseService;
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
@RequestMapping("/house")
public class HouseController {
    @Autowired
    HouseService houseService;
    @GetMapping("/showAllHouses")
    public ResponseEntity<List<HouseDTO>> showAllHouses(){
        List<HouseDTO> houseDTOs = houseService.showAllHouses();
        return new ResponseEntity<>(houseDTOs, HttpStatus.OK);
    }

    @GetMapping("/showHouse/{houseId}")
    public ResponseEntity<HouseDTO> showHouse(@PathVariable String houseId){
        HouseDTO houseDTO = houseService.showHouse(houseId);
        return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    @GetMapping("/showCustomersIn/{houseId}")
    public ResponseEntity<List<CustomersIn>> showCustomersIn(@PathVariable String houseId){
        List<CustomersIn> customersIn = houseService.showCustomersIn(houseId);
        return new ResponseEntity<>(customersIn, HttpStatus.OK);
    }

    @GetMapping("/showReportByDate/{houseId}")
    public ResponseEntity<List<ReportResponse>> showReportsByDate(@PathVariable String houseId){
        List<ReportResponse> reportList = houseService.showReportsByDate(houseId);

        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    @PostMapping(value = "/addHouse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HouseDTO> addHouse(@ModelAttribute HouseRequest houseRequest,
                                             @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        HouseDTO houseDTO = houseService.addHouse(houseRequest, file);
        return new ResponseEntity<>(houseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/closing/{houseId}")
    public ResponseEntity<ReportDTO> closing(@PathVariable String houseId){
        ReportDTO reportDTO = houseService.closing(houseId);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/changeHouse/{houseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HouseDTO> changeHouse(@ModelAttribute HouseDTO houseDTO,
                                                @PathVariable String houseId,
                                                @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        houseDTO = houseService.changeHouse(houseDTO, houseId, file);
        return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    @PutMapping("/generateValidationNumber/{houseId}")
    public ResponseEntity<Integer> generateValidationNumber(@PathVariable String houseId){
        Integer validationNumber = houseService.generateValidationNumber(houseId);
        return new ResponseEntity<>(validationNumber,HttpStatus.OK);
    }

    @DeleteMapping("/deleteHouse/{houseId}")
    public ResponseEntity<?> deleteHouse(@PathVariable String houseId){
        houseService.deleteHouse(houseId);
        return new ResponseEntity<>("House deleted with success!", HttpStatus.OK);
    }
}
