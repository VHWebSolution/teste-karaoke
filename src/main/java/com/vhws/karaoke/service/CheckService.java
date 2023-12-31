package com.vhws.karaoke.service;

import com.vhws.karaoke.entity.dto.CheckDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.ecxeption.ResourceNotFoundException;
import com.vhws.karaoke.entity.model.Check;
import com.vhws.karaoke.entity.model.House;
import com.vhws.karaoke.entity.request.CheckInValidationRequest;
import com.vhws.karaoke.entity.response.CheckInValidationResponse;
import com.vhws.karaoke.entity.response.CheckResponse;
import com.vhws.karaoke.repository.CheckRepository;
import com.vhws.karaoke.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckService {
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private HouseRepository houseRepository;
    public List<CheckDTO> showAllChecks() {
        List<Check> checkList = checkRepository.findAll();
        if (checkList.isEmpty())
            throw new ResourceNotFoundException("No checks were found!");
        List<CheckDTO> checkDTOList = new ArrayList<>();
        for (Check check : checkList) {
            CheckDTO checkDTO = new CheckDTO(check.getCheckId(), check.getHouse(), check.getCustomerName(), check.getHouseName(), check.getCheckNumber(), check.isTaken(), check.isOpen(), check.getNextSong());
            checkDTOList.add(checkDTO);
        }
        return checkDTOList;
    }

    public CheckDTO showCheck(String checkId) {
        Optional<Check> checkOptional = checkRepository.findById(checkId);
        if (checkOptional.isEmpty())
            throw new ResourceNotFoundException("Check not found!");
        Check check = checkOptional.get();
        CheckDTO checkDTO = new CheckDTO(check.getCheckId(), check.getHouse(), check.getCustomerName(), check.getHouseName(), check.getCheckNumber(), check.isTaken(), check.isOpen(), check.getNextSong());
        return checkDTO;
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
    public void addMultipleChecks(String houseId, int numberOfChecks){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));

        List<Integer> actualCheckNumbers = house.getCheckList().stream()
                .map(Check::getCheckNumber)
                .collect(Collectors.toList());

        Collections.sort(actualCheckNumbers);

        int newCheckNumber = 0;

        if(actualCheckNumbers.size() != 0) {
            newCheckNumber = actualCheckNumbers.get(actualCheckNumbers.size() - 1) + 1;
        }


        List<Check> listOfNewChecks = new ArrayList<>();
        for(int i = 1; i <= numberOfChecks; i++){
            newCheckNumber = newCheckNumber + i;
            Check newCheck = new Check(
                    house,
                    house.getHouseName(),
                    newCheckNumber
            );

            listOfNewChecks.add(newCheck);
        }
        checkRepository.saveAll(listOfNewChecks);
    }
    public CheckInValidationResponse checkInValidation(CheckInValidationRequest checkDTO, String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        if(checkDTO.validationNumber() != house.getValidationNumber())
            throw new ResourceBadRequestException("The validation number is incorrect!");

        List<Check> checks = checkRepository.findWhereNotTaken(houseId);
        if (checks.isEmpty()) {
            throw new ResourceNotFoundException("Check not found!");
        }
        Check check = checks.get(0);
        List<Check> checkList = house.getCheckList();
        for(Check checkFor:checkList) {
            if (check.equals(checkFor)) {
                check.setCustomerName(checkDTO.customerName());
                check.setTaken(true);
                check.setOpen(true);
                checkRepository.save(check);
            }
        }

        return new CheckInValidationResponse(check.getCheckId());
    }

    public CheckDTO checkOut(String checkId){
        Optional<Check> checkOptional = checkRepository.findById(checkId);
        if(checkOptional.isEmpty())
            throw new ResourceNotFoundException("Check not found!");
        Check check = checkOptional.get();
        check.setNextSong(null);
        check.setOpen(false);
        checkRepository.save(check);
        CheckDTO checkDTO = createCheckDTO(check);
        return checkDTO;
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
            check.setCustomerName(checkDTO.getCustomerName());
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

    public List<CheckDTO> resetChecks(String houseId){
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found!"));
        List<Check> checkList = house.getCheckList();
        List<CheckDTO> checkDTOList = new ArrayList<>();
        for(Check c:checkList){
            if(c.isTaken()==true){
                c.setCustomerName(null);
                c.setOpen(false);
                c.setTaken(false);
                c.setNextSong(null);
                checkRepository.save(c);
            }
            CheckDTO checkDTO = createCheckDTO(c);
            checkDTOList.add(checkDTO);
        }
        return checkDTOList;
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

    private CheckDTO createCheckDTO(Check check){
        return new CheckDTO(
                check.getCheckId(),
                check.getHouse(),
                check.getCustomerName(),
                check.getHouseName(),
                check.getCheckNumber(),
                check.isTaken(),
                check.isOpen(),
                check.getNextSong()
        );
    }
}
