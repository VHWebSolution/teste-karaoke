package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.request.HouseRequest;
import com.vhws.karaoke.entity.dto.HouseDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.response.CustomersIn;
import com.vhws.karaoke.repository.CheckRepository;
import com.vhws.karaoke.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private CheckRepository checkRepository;

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

    public List<CustomersIn> showCustomersIn(String houseId){
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isEmpty())
            throw new ResourceNotFoundException("No costumers found!");
        House house = houseOptional.get();
        List<Check> checkList = house.getCheckList();
        List<CustomersIn> costumersInList = new ArrayList<>();
        for(Check check:checkList){
            if(check.getCustomerName()==null){
                check.setCustomerName("Em aberto");
            }
            CustomersIn costumersIn = new CustomersIn(check.getCustomerName(), check.getCheckNumber(), check.getNextSong());
            costumersInList.add(costumersIn);
        }
        return costumersInList;
    }

    public HouseDTO addHouse(HouseRequest houseRequest){
        House house = new House(houseRequest.getHouseName(),houseRequest.getCnpj(), houseRequest.getPhone(), houseRequest.getAddress());
        house = houseRepository.save(house);
        HouseDTO houseDTO = createHouseDTO(house);
        return houseDTO;
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
}
