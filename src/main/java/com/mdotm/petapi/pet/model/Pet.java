package com.mdotm.petapi.pet.model;

import com.mdotm.petapi.pet.common.enums.Species;
import jakarta.persistence.*;

@Entity()
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;
    @Column(nullable = true)
    private Integer age;
    @Column(nullable = true)
    private String ownerName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
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
