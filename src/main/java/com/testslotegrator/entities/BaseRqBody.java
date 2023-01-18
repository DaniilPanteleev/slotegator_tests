package com.testslotegrator.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public abstract class BaseRqBody {

    @SneakyThrows
    private String toJson(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);

        return mapper.writeValueAsString(this);
    }

    public String toJson() {
        return toJson(JsonInclude.Include.ALWAYS);
    }

    public String toJsonWithoutNulls() {
        return toJson(JsonInclude.Include.NON_NULL);
    }

}
