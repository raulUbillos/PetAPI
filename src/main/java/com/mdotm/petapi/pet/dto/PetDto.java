package com.mdotm.petapi.pet.dto;
import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.common.utils.Helpers;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import java.util.Arrays;


public class PetDto {

    private String id;
    @NotBlank()
    private String name;
    
    @Pattern(regexp = "DOG|CAT|RABBIT")
    @NotBlank()
    private String species;
    
    @Min(value = 0, message = "Age should be more or equal 0")
    private Integer age;
    private String ownerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return String.format("Pet: Name: %s, Species: %s, Age: %s, Owner: %s", name, species, age, ownerName);
    }
}
