package com.mdotm.petapi.pet.controller;

import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    @Valid()
    public PetDto createPet(@RequestBody PetDto pet) {
        return petService.createPet(pet);
    }

    @PatchMapping("/{id}")
    @Valid()
    public PetDto updatePet(@RequestBody PetUpdateDto updateDto, @PathVariable Long id) {
        PetDto petDto = petService.updatePet(updateDto, id);
        return petDto;
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    @GetMapping("/{id}")
    public PetDto getPet(@PathVariable Long id) {
        PetDto pet = petService.getPetById(id);
        return pet;
    }

    @GetMapping()
    public List<PetDto> getPets( PetFiltersDto petFiltersDto) {
        return petService.getPets(petFiltersDto == null ? new PetFiltersDto(): petFiltersDto);
    }

}
