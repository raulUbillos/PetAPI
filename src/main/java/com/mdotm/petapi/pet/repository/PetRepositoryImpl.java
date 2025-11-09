package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PetRepositoryImpl implements PetRepositoryDao {
    private final PetRepository petRepository;

    public PetRepositoryImpl(@Lazy PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<Pet> findByFilters(PetFiltersDto petFiltersDto) {
        if(petFiltersDto == null) {
            return petRepository.findAll();
        }
        return petRepository.findByFilters(petFiltersDto.getName(), Species.valueOf(petFiltersDto.getSpecies()), petFiltersDto.getAge(), petFiltersDto.getOwnerName());
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
