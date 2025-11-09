package com.mdotm.petapi.pet.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class PetNotFoundException extends HttpClientErrorException {
    public PetNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format("Pet with id %s not found", id));
    }
}
