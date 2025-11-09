package com.mdotm.petapi.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.repository.PetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class PetControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        petRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        petRepository.deleteAll();
    }

    @Test
    void createPet_ShouldReturnCreatedPet_WhenValidRequest() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("Buddy");
        petDto.setSpecies("DOG");
        petDto.setAge(3);
        petDto.setOwnerName("John Doe");

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.species").value("DOG"))
                .andExpect(jsonPath("$.age").value(3))
                .andExpect(jsonPath("$.ownerName").value("John Doe"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createPet_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("");
        petDto.setSpecies("INVALID");

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPetById_ShouldReturnPet_WhenPetExists() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");
        Pet savedPet = petRepository.save(pet);

        mockMvc.perform(get("/pet/{id}", savedPet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(String.valueOf(savedPet.getId())))
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.species").value("DOG"))
                .andExpect(jsonPath("$.age").value(3))
                .andExpect(jsonPath("$.ownerName").value("John Doe"));
    }

    @Test
    void getPetById_ShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        mockMvc.perform(get("/pet/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPets_ShouldReturnAllPets_WhenNoFilters() throws Exception {
        Pet pet1 = new Pet();
        pet1.setName("Buddy");
        pet1.setSpecies(Species.DOG);
        pet1.setAge(3);
        pet1.setOwnerName("John Doe");
        petRepository.save(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Whiskers");
        pet2.setSpecies(Species.CAT);
        pet2.setAge(2);
        pet2.setOwnerName("Jane Smith");
        petRepository.save(pet2);

        mockMvc.perform(get("/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }

    @Test
    void getPets_ShouldReturnFilteredPets_WhenFiltersProvided() throws Exception {
        Pet pet1 = new Pet();
        pet1.setName("Buddy");
        pet1.setSpecies(Species.DOG);
        pet1.setAge(3);
        pet1.setOwnerName("John Doe");
        petRepository.save(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Whiskers");
        pet2.setSpecies(Species.CAT);
        pet2.setAge(2);
        pet2.setOwnerName("Jane Smith");
        petRepository.save(pet2);

        mockMvc.perform(get("/pet")
                        .param("species", "DOG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].species").value("DOG"));
    }

    @Test
    void updatePet_ShouldReturnUpdatedPet_WhenPetExists() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");
        Pet savedPet = petRepository.save(pet);

        PetUpdateDto updateDto = new PetUpdateDto();
        updateDto.setName("Max");
        updateDto.setAge(5);

        mockMvc.perform(patch("/pet/{id}", savedPet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.age").value(5))
                .andExpect(jsonPath("$.species").value("DOG"))
                .andExpect(jsonPath("$.ownerName").value("John Doe"));
    }

    @Test
    void updatePet_ShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        PetUpdateDto updateDto = new PetUpdateDto();
        updateDto.setName("Max");

        mockMvc.perform(patch("/pet/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePet_ShouldReturnBadRequest_WhenUpdateDtoIsNull() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");
        Pet savedPet = petRepository.save(pet);

        mockMvc.perform(patch("/pet/{id}", savedPet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePet_ShouldReturnOk_WhenPetExists() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");
        Pet savedPet = petRepository.save(pet);

        mockMvc.perform(delete("/pet/{id}", savedPet.getId()))
                .andExpect(status().isOk());

        assertFalse(petRepository.findById(savedPet.getId()).isPresent());
    }

    @Test
    void deletePet_ShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        mockMvc.perform(delete("/pet/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPet_WithAllSpecies_ShouldCreateSuccessfully() throws Exception {
        String[] species = {"DOG", "CAT", "RABBIT"};

        for (String specie : species) {
            PetDto petDto = new PetDto();
            petDto.setName("Test " + specie);
            petDto.setSpecies(specie);
            petDto.setAge(1);
            petDto.setOwnerName("Owner");

            mockMvc.perform(post("/pet")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(petDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.species").value(specie));
        }
    }

    @Test
    void updatePet_WithSpeciesChange_ShouldUpdateSuccessfully() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");
        Pet savedPet = petRepository.save(pet);

        PetUpdateDto updateDto = new PetUpdateDto();
        updateDto.setSpecies("CAT");

        mockMvc.perform(patch("/pet/{id}", savedPet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value("CAT"));
    }
}

