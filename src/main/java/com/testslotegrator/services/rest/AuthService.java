package com.testslotegrator.services.rest;

import com.testslotegrator.entities.dto.auth.CredentialsGrantRq;
import com.testslotegrator.entities.dto.register.RegisterPlayerRq;
import com.testslotegrator.services.rest.common.BaseService;
import com.testslotegrator.services.rest.common.ResponseWrapper;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.testslotegrator.config.Configuration.HTTP_BASIC_USERNAME;
import static io.restassured.http.Method.POST;

@NoArgsConstructor
public class AuthService extends BaseService<AuthService> {

    private String tokenPath = "/v2/oauth2/token";
    private String registerPath = "/v2/players";

    public ResponseWrapper postGetAuthToken(CredentialsGrantRq body) {
        return wrap(
                withChangesToRequestSpec(r ->
                        r.addHeader(authHeaderName, "Basic %s".formatted(Base64.getEncoder()
                                        .encodeToString(String.format("%s:%s", HTTP_BASIC_USERNAME, "")
                                                .getBytes(StandardCharsets.UTF_8))))
                                .setBody(body.toJsonWithoutNulls())
                ).sendRequest(POST, tokenPath)
        );
    }

    public ResponseWrapper postRegisterNewPlayer(RegisterPlayerRq body) {
        return wrap(
                withChangesToRequestSpec(r ->
                        r.setBody(body.toJsonWithoutNulls())
                ).sendRequest(POST, registerPath)
        );
    }

}
