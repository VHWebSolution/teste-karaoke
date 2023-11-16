package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.request.HouseRequest;
import com.vhws.karaoke.entity.dto.HouseDTO;
import com.vhws.karaoke.entity.response.CustomersIn;
import com.vhws.karaoke.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addHouse")
    public ResponseEntity<HouseDTO> addHouse(@RequestBody HouseRequest houseRequest){
        HouseDTO houseDTO = houseService.addHouse(houseRequest);
        return new ResponseEntity<>(houseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/changeHouse/{houseId}")
    public ResponseEntity<HouseDTO> changeHouse(@RequestBody HouseDTO houseDTO, @PathVariable String houseId){
        houseDTO = houseService.changeHouse(houseDTO, houseId);
        return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteHouse/{houseId}")
    public ResponseEntity<?> deleteHouse(@PathVariable String houseId){
        houseService.deleteHouse(houseId);
        return new ResponseEntity<>("House deleted with success!", HttpStatus.OK);
    }
}
