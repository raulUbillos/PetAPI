package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;

import java.util.List;

public interface PetRepositoryDao {
    List<Pet> findByFilters(PetFiltersDto petFiltersDto);
}
