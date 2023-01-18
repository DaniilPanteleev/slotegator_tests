package com.testslotegrator.services.rest.common;

import io.restassured.response.ValidatableResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ResponseWrapper {
    private final ValidatableResponse response;

    public <T> T as(Class<T> clazz) {
        return response.extract().jsonPath().getObject(".", clazz);
    }

    public int getStatusCode() {
        return response.extract().response().getStatusCode();
    }
}
