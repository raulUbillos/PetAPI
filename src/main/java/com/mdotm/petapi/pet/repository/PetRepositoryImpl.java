package com.mdotm.petapi.pet.repository;

import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.model.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PetRepositoryImpl implements PetRepositoryDao {
    EntityManager entityManager;

    public PetRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Pet> findByFilters(PetFiltersDto petFiltersDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);

        Root<Pet> petRoot = criteriaQuery.from(Pet.class);
        List<Predicate> predicates = new ArrayList<>();

        if(petFiltersDto.getAge() != null) {
            predicates.add(criteriaBuilder.equal(petRoot.get("age"), petFiltersDto.getAge()));
        }
        if(petFiltersDto.getName() != null) {
            predicates.add(criteriaBuilder.like(petRoot.get("name"), "%"+petFiltersDto.getName()+"%"));
        }
        if(petFiltersDto.getOwnerName() != null) {
            predicates.add(criteriaBuilder.like(petRoot.get("ownerName"), "%"+petFiltersDto.getOwnerName()+"%"));
        }
        if(petFiltersDto.getSpecies() != null) {
            predicates.add(criteriaBuilder.equal(petRoot.get("species"), petFiltersDto.getSpecies()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
