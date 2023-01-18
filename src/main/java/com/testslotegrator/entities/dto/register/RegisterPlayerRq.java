package com.testslotegrator.entities.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testslotegrator.entities.BaseRqBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterPlayerRq extends BaseRqBody {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password_change")
    private String passwordChange;

    @JsonProperty("password_repeat")
    private String passwordRepeat;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("currency_code")
    private String currencyCode;

}
