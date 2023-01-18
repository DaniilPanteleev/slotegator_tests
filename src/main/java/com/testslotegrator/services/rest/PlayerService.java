package com.testslotegrator.services.rest;

import com.testslotegrator.entities.dto.auth.CredentialsGrantRq;
import com.testslotegrator.entities.dto.register.RegisterPlayerRq;
import com.testslotegrator.services.rest.common.BaseService;
import com.testslotegrator.services.rest.common.ResponseWrapper;
import io.restassured.authentication.PreemptiveBasicAuthScheme;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.testslotegrator.config.Configuration.HTTP_BASIC_USERNAME;
import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.POST;

public class PlayerService extends BaseService<PlayerService> {

    private String getPlayerPath = " /v2/players/%s";

    public PlayerService(String bearerToken) {
        super(bearerToken);
    }

    public ResponseWrapper postGetPlayerProfile(Integer id) {
        return wrap(sendRequest(GET, getPlayerPath.formatted(id)));
    }

}
