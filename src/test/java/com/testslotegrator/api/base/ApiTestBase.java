package com.testslotegrator.api.base;

import com.testslotegrator.annotations.Precondition;
import com.testslotegrator.entities.dto.auth.CredentialsGrantRp;
import com.testslotegrator.entities.dto.register.RegisterPlayerRp;
import com.testslotegrator.entities.dto.register.RegisterPlayerRq;
import com.testslotegrator.services.rest.AuthService;
import com.testslotegrator.services.rest.PlayerService;
import com.testslotegrator.services.rest.common.ResponseWrapper;
import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.util.Optional;

import static com.testslotegrator.entities.enums.GrantTypeNames.PASSWORD;
import static com.testslotegrator.helpers.RequestsHelper.getCredGrantUserRq;
import static com.testslotegrator.helpers.RequestsHelper.getRegisterPlayerRq;
import static io.qameta.allure.Allure.step;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Log4j2
public abstract class ApiTestBase {

    protected Integer wrongPlayerId = 100000;

    protected String grantCredentialsSchemaName = "schemas/CredentialsGrantSchema.json";
    protected String registerNewPlayerSchemaName = "schemas/RegisterPlayerSchema.json";

    protected ResponseWrapper responseWrapper;

    protected AuthService authService;
    protected PlayerService playerService;

    protected RegisterPlayerRq createPlayerEntity;
    protected RegisterPlayerRp createdPlayerEntity;

    @BeforeEach
    void basePrecondition(TestInfo testInfo) {
        String bearerToken = null;
        Precondition precondition = testInfo.getTestMethod().get().getAnnotation(Precondition.class);
        if (Optional.ofNullable(precondition).isPresent()) {
            if (precondition.createUser()) {
                createPlayerEntity = getRegisterPlayerRq();
                createdPlayerEntity = authService.postRegisterNewPlayer(createPlayerEntity).as(RegisterPlayerRp.class);
            }
            if (precondition.authUser()) {
                bearerToken = authService.postGetAuthToken(getCredGrantUserRq(PASSWORD, createPlayerEntity.getUsername(),
                                createPlayerEntity.getPasswordChange()))
                        .as(CredentialsGrantRp.class)
                        .getAccessToken();
            }
        }
        authService = new AuthService();
        playerService = new PlayerService(bearerToken);
    }

    protected void checkJsonSchema(ResponseWrapper responseWrapper, String schemaFileName) {
        step("Check JSON schema", () -> {
            Allure.addAttachment("JSON schema", Thread.currentThread().getContextClassLoader().getResourceAsStream(schemaFileName));
            responseWrapper.getResponse().assertThat().body(matchesJsonSchemaInClasspath(schemaFileName));
        });
    }

}
