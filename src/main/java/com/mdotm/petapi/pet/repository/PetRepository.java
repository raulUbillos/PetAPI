package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PetRepository extends JpaRepository<Pet, Long> {
    // Versión lista
    @Query("""
      select p
      from Pet p
      where (:name is null or lower(p.name) like lower(concat('%', :name, '%')))
        and (:species is null or p.species = :species)
        and (:age is null or p.age = :age)
        and (:ownerName is null or lower(p.ownerName) like lower(concat('%', :ownerName, '%')))
      """)
    List<Pet> findByFilters(
            @Param("name") String name,
            @Param("species") Species species,
            @Param("age") Integer age,
            @Param("ownerName") String ownerName
    );
}
