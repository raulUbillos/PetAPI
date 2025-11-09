package com.mdotm.petapi.pet.service;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.common.exceptions.PetNotFoundException;
import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.dto.PetFiltersDto;
import com.mdotm.petapi.pet.dto.PetUpdateDto;
import com.mdotm.petapi.pet.model.Pet;
import com.mdotm.petapi.pet.repository.PetRepositoryDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepositoryDao petRepositoryDao;

    private PetService petService;

    private PetDto petDto;
    private Pet pet;
    private PetUpdateDto petUpdateDto;

    @BeforeEach
    void setUp() {
        petService = new PetService(petRepositoryDao);
        
        petDto = new PetDto();
        petDto.setName("Buddy");
        petDto.setSpecies("DOG");
        petDto.setAge(3);
        petDto.setOwnerName("John Doe");

        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
        pet.setSpecies(Species.DOG);
        pet.setAge(3);
        pet.setOwnerName("John Doe");

        petUpdateDto = new PetUpdateDto();
        petUpdateDto.setName("Max");
        petUpdateDto.setAge(5);
    }

    @Test
    void createPet_ShouldCallRepositorySave_AndReturnPetDto() {
        when(petRepositoryDao.save(any(Pet.class))).thenReturn(pet);

        PetDto result = petService.createPet(petDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Buddy", result.getName());
        assertEquals("DOG", result.getSpecies());
        assertEquals(3, result.getAge());
        assertEquals("John Doe", result.getOwnerName());

        verify(petRepositoryDao, times(1)).save(any(Pet.class));
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPetDto() {
        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));

        PetDto result = petService.getPetById(1L);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Buddy", result.getName());

        verify(petRepositoryDao, times(1)).findById(1L);
    }

    @Test
    void getPetById_WhenPetNotFound_ShouldThrowPetNotFoundException() {
        when(petRepositoryDao.findById(999L)).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> {
            petService.getPetById(999L);
        });

        assertTrue(exception.getMessage().contains("Pet with id 999 not found"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(petRepositoryDao, times(1)).findById(999L);
    }

    @Test
    void getPets_WithNullFilters_ShouldCallRepositoryFindByFilters() {
        List<Pet> pets = Arrays.asList(pet);
        when(petRepositoryDao.findByFilters(null)).thenReturn(pets);

        List<PetDto> result = petService.getPets(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());

        verify(petRepositoryDao, times(1)).findByFilters(null);
    }

    @Test
    void getPets_WithFilters_ShouldCallRepositoryFindByFilters() {
        PetFiltersDto filters = new PetFiltersDto();
        filters.setName("Buddy");
        filters.setSpecies("DOG");

        List<Pet> pets = Arrays.asList(pet);
        when(petRepositoryDao.findByFilters(filters)).thenReturn(pets);

        List<PetDto> result = petService.getPets(filters);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(petRepositoryDao, times(1)).findByFilters(filters);
    }

    @Test
    void deletePetById_WhenPetExists_ShouldCallRepositoryDelete() {
        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepositoryDao).deleteById(1L);

        assertDoesNotThrow(() -> petService.deletePetById(1L));

        verify(petRepositoryDao, times(1)).findById(1L);
        verify(petRepositoryDao, times(1)).deleteById(1L);
    }

    @Test
    void deletePetById_WhenPetNotFound_ShouldThrowPetNotFoundException() {
        when(petRepositoryDao.findById(999L)).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> {
            petService.deletePetById(999L);
        });

        assertTrue(exception.getMessage().contains("Pet with id 999 not found"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(petRepositoryDao, times(1)).findById(999L);
        verify(petRepositoryDao, never()).deleteById(anyLong());
    }

    @Test
    void updatePet_WhenPetExists_ShouldUpdateAndReturnPetDto() {
        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("Max");
        updatedPet.setSpecies(Species.DOG);
        updatedPet.setAge(5);
        updatedPet.setOwnerName("John Doe");

        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepositoryDao.save(any(Pet.class))).thenReturn(updatedPet);

        PetDto result = petService.updatePet(petUpdateDto, 1L);

        assertNotNull(result);
        assertEquals("Max", result.getName());
        assertEquals(5, result.getAge());

        verify(petRepositoryDao, times(1)).findById(1L);
        verify(petRepositoryDao, times(1)).save(any(Pet.class));
    }

    @Test
    void updatePet_WhenPetNotFound_ShouldThrowPetNotFoundException() {
        when(petRepositoryDao.findById(999L)).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> {
            petService.updatePet(petUpdateDto, 999L);
        });

        assertTrue(exception.getMessage().contains("Pet with id 999 not found"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(petRepositoryDao, times(1)).findById(999L);
        verify(petRepositoryDao, never()).save(any(Pet.class));
    }

    @Test
    void updatePet_WhenPetDtoIsNull_ShouldThrowHttpClientErrorException() {
        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            petService.updatePet(null, 1L);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("To do the update you need a request"));

        verify(petRepositoryDao, times(1)).findById(1L);
        verify(petRepositoryDao, never()).save(any(Pet.class));
    }

    @Test
    void updatePet_WithPartialUpdate_ShouldOnlyUpdateProvidedFields() {
        PetUpdateDto partialUpdate = new PetUpdateDto();
        partialUpdate.setName("NewName");

        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("NewName");
        updatedPet.setSpecies(Species.DOG);
        updatedPet.setAge(3);
        updatedPet.setOwnerName("John Doe");

        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepositoryDao.save(any(Pet.class))).thenReturn(updatedPet);

        PetDto result = petService.updatePet(partialUpdate, 1L);

        assertNotNull(result);
        assertEquals("NewName", result.getName());
        assertEquals(3, result.getAge());
        assertEquals("DOG", result.getSpecies());

        verify(petRepositoryDao, times(1)).findById(1L);
        verify(petRepositoryDao, times(1)).save(any(Pet.class));
    }

    @Test
    void updatePet_WithSpeciesUpdate_ShouldUpdateSpecies() {
        PetUpdateDto updateWithSpecies = new PetUpdateDto();
        updateWithSpecies.setSpecies("CAT");

        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("Buddy");
        updatedPet.setSpecies(Species.CAT);
        updatedPet.setAge(3);
        updatedPet.setOwnerName("John Doe");

        when(petRepositoryDao.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepositoryDao.save(any(Pet.class))).thenReturn(updatedPet);

        PetDto result = petService.updatePet(updateWithSpecies, 1L);

        assertNotNull(result);
        assertEquals("CAT", result.getSpecies());

        verify(petRepositoryDao, times(1)).findById(1L);
        verify(petRepositoryDao, times(1)).save(any(Pet.class));
    }
}

