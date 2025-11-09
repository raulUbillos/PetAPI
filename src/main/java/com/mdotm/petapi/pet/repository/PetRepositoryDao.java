package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepositoryDao {
    List<Pet> findByFilters(
             PetFiltersDto petFiltersDto
    );
    Pet save(Pet pet);
    Optional<Pet> findById(Long id);
    void deleteById(Long id);

}
