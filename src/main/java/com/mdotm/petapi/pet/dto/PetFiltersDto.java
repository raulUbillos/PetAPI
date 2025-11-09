package com.mdotm.petapi.pet.dto;

public class PetFiltersDto {
    private String name;
    private String species;
    private Integer age;
    private String ownerName;

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
        return String.format("Pet filters: Name: %s, Species: %s, Age: %s, Owner: %s", name, species, age, ownerName);
    }
}
