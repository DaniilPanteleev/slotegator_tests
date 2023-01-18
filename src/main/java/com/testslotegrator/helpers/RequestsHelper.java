package com.testslotegrator.helpers;

import com.testslotegrator.entities.dto.auth.CredentialsGrantRq;
import com.testslotegrator.entities.dto.register.RegisterPlayerRq;
import com.testslotegrator.entities.enums.GrantTypeNames;
import com.testslotegrator.entities.enums.ScopeNames;
import com.testslotegrator.utils.Base64Utils;

import static com.testslotegrator.utils.RandomUtils.getFakerInstance;

public class RequestsHelper {

    public static CredentialsGrantRq getCredGrantGuestRq(ScopeNames scopeNames, GrantTypeNames grantTypeNames) {
        return getCredGrantRq(scopeNames.getScope(), grantTypeNames.getGrantType(), null, null);
    }

    public static CredentialsGrantRq getCredGrantUserRq(GrantTypeNames grantTypeNames, String username,
                                                         String password) {
        return getCredGrantRq(null, grantTypeNames.getGrantType(), username, password);
    }

    private static CredentialsGrantRq getCredGrantRq(String scopeNames, String grantTypeNames,
                                                    String username, String password) {
        return CredentialsGrantRq.builder()
                .scope(scopeNames)
                .grantType(grantTypeNames)
                .username(username)
                .password(password)
                .build();
    }

    public static RegisterPlayerRq getRegisterPlayerRq() {
        String password = getFakerInstance().regexify("[0-9]{8}");

        return RegisterPlayerRq.builder()
                .name(getFakerInstance().name().firstName())
                .surname(getFakerInstance().name().lastName())
                .username(getFakerInstance().name().username())
                .passwordChange(Base64Utils.encode(password))
                .passwordRepeat(Base64Utils.encode(password))
                .email(getFakerInstance().internet().emailAddress())
                .currencyCode("EUR")
                .build();
    }

}
