package com.mdotm.petapi.pet.common.mappers;

import com.mdotm.petapi.pet.common.enums.Species;
import com.mdotm.petapi.pet.dto.PetDto;
import com.mdotm.petapi.pet.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PetMapper {
    PetMapper mapper = Mappers.getMapper(PetMapper.class);

    PetDto PettoPetDto(Pet pet);
    @Mapping(ignore = true, target = "id")
    Pet PetDtotoPet(PetDto petDto);

    List<PetDto> PettoPetDto(List<Pet> pet);
    List<Pet> PetDtotoPet(List<PetDto> petDto);

    default Species map(String species) {
        return Species.valueOf(species.toUpperCase());
    }
    default String map(Species value){
        return value.name();
    }
}
