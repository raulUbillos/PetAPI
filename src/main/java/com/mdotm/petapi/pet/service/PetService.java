package com.mdotm.petapi.pet.service;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.common.exceptions.PetNotFoundException;
import com.mdotm.petapi.pet.common.mappers.PetMapper;
import com.mdotm.petapi.pet.controller.PetController;
import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.repository.PetRepository;
import com.mdotm.petapi.pet.repository.PetRepositoryDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class PetService {
    private static Logger logger = LogManager.getLogger(PetController.class);

    private final PetRepositoryDao petRepository;

    public PetService(PetRepositoryDao petRepository) {
        this.petRepository = petRepository;
    }

    public PetDto createPet(PetDto petDto) {
        logger.info("Trying to create pet: " + petDto);
        Pet petToCreate = PetMapper.mapper.PetDtotoPet(petDto);
        Pet petCreated = petRepository.save(petToCreate);
        logger.info("Pet created: " + petCreated);
        return PetMapper.mapper.PettoPetDto(petCreated);
    }

    public PetDto getPetById(Long id) {
        logger.info("Trying to get pet by id: " + id);
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            logger.error("Pet not found");
            throw new PetNotFoundException(id);
        }
        return PetMapper.mapper.PettoPetDto(pet);
    }

    public List<PetDto> getPets(PetFiltersDto petFiltersDto) {
        logger.info("Trying to get pets");
        logger.info("PetFiltersDto: " + petFiltersDto);
        List<Pet> pets = petRepository.findByFilters(petFiltersDto);
        return PetMapper.mapper.PettoPetDto(pets);
    }

    public void deletePetById(Long id) {
        logger.info("Trying to delete pet by id: " + id);
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            logger.error("Pet not found");
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }

    public PetDto updatePet(PetUpdateDto petDto, Long id) {
        logger.info("Trying to update pet: " + petDto);
        Pet pet = petRepository.findById(id).orElse(null);
        if(pet == null) {
            logger.error("Pet not found");
            throw new PetNotFoundException(id);
        }
        if(petDto == null) {
            logger.error("PetDto is null");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "To do the update you need a request");
        }
        if (petDto.getName() != null) {
            pet.setName(petDto.getName());
        }
        if(petDto.getAge() != null) {
            pet.setAge(petDto.getAge());
        }
        if(petDto.getOwnerName() != null) {
            pet.setOwnerName(petDto.getOwnerName());
        }
        if(petDto.getSpecies() != null) {
            pet.setSpecies(Species.valueOf(petDto.getSpecies()));
        }
        petRepository.save(pet);
        return PetMapper.mapper.PettoPetDto(pet);
    }
}
