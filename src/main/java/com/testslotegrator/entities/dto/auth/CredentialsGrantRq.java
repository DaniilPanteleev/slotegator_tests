package com.testslotegrator.entities.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testslotegrator.entities.BaseRqBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsGrantRq extends BaseRqBody {

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
