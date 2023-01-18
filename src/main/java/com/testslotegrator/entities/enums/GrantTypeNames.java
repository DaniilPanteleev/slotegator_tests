package com.testslotegrator.entities.enums;

import lombok.Getter;

@Getter
public enum GrantTypeNames {

    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    ;

    private String grantType;

    GrantTypeNames(String grantType) {
        this.grantType = grantType;
    }

}
