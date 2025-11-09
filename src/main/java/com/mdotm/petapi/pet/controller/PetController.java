package com.mdotm.petapi.pet.controller;

import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController()
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    private static Logger logger = LogManager.getLogger(PetController.class);

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    public PetDto createPet(@Valid @RequestBody PetDto pet) {
        logger.info("Creating pet: " + pet);
        return petService.createPet(pet);
    }

    @PatchMapping("/{id}")
    public PetDto updatePet(@RequestBody PetUpdateDto updateDto, @PathVariable Long id) {
        logger.info(String.format("Updating pet with id %s with parameter %s", id, updateDto));
        return petService.updatePet(updateDto, id);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        logger.info(String.format("Deleting pet with id %s", id));
        petService.deletePetById(id);
    }

    @GetMapping("/{id}")
    public PetDto getPet(@PathVariable Long id) {
        logger.info(String.format("Getting pet with id %s", id));
        return petService.getPetById(id);
    }

    @GetMapping()
    public List<PetDto> getPets( PetFiltersDto petFiltersDto) {
        logger.info(String.format("Getting pets with filters %s", petFiltersDto));
        return petService.getPets(petFiltersDto);
    }

}
