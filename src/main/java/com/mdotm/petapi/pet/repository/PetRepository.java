package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, PetRepositoryDao {
}
