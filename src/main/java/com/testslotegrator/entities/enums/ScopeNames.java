package com.testslotegrator.entities.enums;

import lombok.Getter;

@Getter
public enum ScopeNames {

    SCOPE1_READ("scope1:read"),
    SCOPE1_WRITE("scope1:write"),
    SCOPE2_READ("scope2:read"),
    GUEST("guest:default"),
    ;

    private String scope;

    ScopeNames(String scope) {
        this.scope = scope;
    }

}
