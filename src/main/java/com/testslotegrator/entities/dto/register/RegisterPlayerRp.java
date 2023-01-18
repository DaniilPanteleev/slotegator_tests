package com.testslotegrator.entities.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testslotegrator.entities.BaseRqBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterPlayerRp {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("country_id")
    private Object countryId;

    @JsonProperty("timezone_id")
    private Object timezoneId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("gender")
    private Object gender;

    @JsonProperty("phone_number")
    private Object phoneNumber;

    @JsonProperty("birthdate")
    private Object birthdate;

    @JsonProperty("bonuses_allowed")
    private Boolean bonusesAllowed;

    @JsonProperty("is_verified")
    private Boolean isVerified;

}
