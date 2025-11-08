package com.mdotm.petapi.pet.service;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.common.mappers.PetMapper;
import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDto createPet(PetDto petDto) {
        Pet petToCreate = PetMapper.mapper.PetDtotoPet(petDto);
        Pet petCreated = petRepository.save(petToCreate);
        return PetMapper.mapper.PettoPetDto(petCreated);
    }

    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        return PetMapper.mapper.PettoPetDto(pet);
    }

    public List<PetDto> getPets(PetFiltersDto petFiltersDto) {
        System.out.println(petFiltersDto.getName());
        List<Pet> pets = petRepository.findByFilters(petFiltersDto);
        return PetMapper.mapper.PettoPetDto(pets);
    }

    public void deletePetById(Long id) {
        petRepository.deleteById(id);
    }

    public PetDto updatePet(PetUpdateDto petDto, Long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null && petDto != null) {
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
        return null;
    }
}
