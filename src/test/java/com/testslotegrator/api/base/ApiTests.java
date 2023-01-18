package com.testslotegrator.api.base;

import com.testslotegrator.annotations.Precondition;
import com.testslotegrator.entities.dto.auth.CredentialsGrantRp;
import com.testslotegrator.entities.dto.register.RegisterPlayerRp;
import com.testslotegrator.entities.dto.register.RegisterPlayerRq;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.testslotegrator.config.Constants.NEGATIVE_TEST;
import static com.testslotegrator.config.Constants.POSITIVE_TEST;
import static com.testslotegrator.entities.enums.GrantTypeNames.CLIENT_CREDENTIALS;
import static com.testslotegrator.entities.enums.GrantTypeNames.PASSWORD;
import static com.testslotegrator.entities.enums.ScopeNames.GUEST;
import static com.testslotegrator.helpers.RequestsHelper.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiTests extends ApiTestBase {

    @Test
    @DisplayName("Get guest token")
    @Severity(CRITICAL)
    @Description(POSITIVE_TEST)
    void getGuestTokenTest() {
        step("Send client token request", () -> {
            responseWrapper = authService.postGetAuthToken(getCredGrantGuestRq(GUEST, CLIENT_CREDENTIALS));

            checkJsonSchema(responseWrapper, grantCredentialsSchemaName);

            step("Check response", () -> {
                SoftAssertions.assertSoftly(s -> {
                    s.assertThat(responseWrapper.getStatusCode())
                            .isEqualTo(SC_OK);
                    s.assertThat(responseWrapper.as(CredentialsGrantRp.class).getAccessToken())
                            .isNotEmpty();
                });
            });
        });
    }

    @Test
    @DisplayName("Register new player")
    @Severity(CRITICAL)
    @Description(POSITIVE_TEST)
    void registerNewPlayerTest() {
        step("Send register player request", () -> {
            RegisterPlayerRq expectedBody = getRegisterPlayerRq();

            responseWrapper = authService.postRegisterNewPlayer(expectedBody);

            checkJsonSchema(responseWrapper, registerNewPlayerSchemaName);

            step("Check response", () -> {
                RegisterPlayerRp actualBody = responseWrapper.as(RegisterPlayerRp.class);

                SoftAssertions.assertSoftly(s -> {
                    s.assertThat(responseWrapper.getStatusCode())
                            .isEqualTo(SC_CREATED);
                    s.assertThat(actualBody.getUsername())
                            .isEqualTo(expectedBody.getUsername());
                    s.assertThat(actualBody.getEmail())
                            .isEqualTo(expectedBody.getEmail());
                    s.assertThat(actualBody.getName())
                            .isEqualTo(expectedBody.getName());
                    s.assertThat(actualBody.getSurname())
                            .isEqualTo(expectedBody.getSurname());
                });
            });
        });
    }

    @Test
    @Precondition(createUser = true)
    @DisplayName("Auth with player credentials")
    @Severity(CRITICAL)
    @Description(POSITIVE_TEST)
    void authByPlayerCredentialsTest() {
        step("Send client token request", () -> {
            responseWrapper = authService.postGetAuthToken(getCredGrantUserRq(PASSWORD, createPlayerEntity.getUsername(),
                    createPlayerEntity.getPasswordChange()));

            checkJsonSchema(responseWrapper, grantCredentialsSchemaName);

            step("Check response", () -> {
                SoftAssertions.assertSoftly(s -> {
                    s.assertThat(responseWrapper.getStatusCode())
                            .isEqualTo(SC_OK);
                    s.assertThat(responseWrapper.as(CredentialsGrantRp.class).getAccessToken())
                            .isNotEmpty();
                });
            });
        });
    }

    @Test
    @Precondition(createUser = true, authUser = true)
    @DisplayName("Get player profile")
    @Severity(CRITICAL)
    @Description(POSITIVE_TEST)
    void getPlayerProfileTest() {
        step("Send get client profile request", () -> {
            responseWrapper = playerService.postGetPlayerProfile(createdPlayerEntity.getId());

            checkJsonSchema(responseWrapper, registerNewPlayerSchemaName);

            step("Check response", () -> {
                RegisterPlayerRp actualBody = responseWrapper.as(RegisterPlayerRp.class);

                SoftAssertions.assertSoftly(s -> {
                    s.assertThat(responseWrapper.getStatusCode())
                            .isEqualTo(SC_OK);
                    s.assertThat(actualBody.getUsername())
                            .isEqualTo(createPlayerEntity.getUsername());
                    s.assertThat(actualBody.getEmail())
                            .isEqualTo(createPlayerEntity.getEmail());
                    s.assertThat(actualBody.getName())
                            .isEqualTo(createPlayerEntity.getName());
                    s.assertThat(actualBody.getSurname())
                            .isEqualTo(createPlayerEntity.getSurname());
                });
            });
        });
    }

    @Test
    @Precondition(createUser = true, authUser = true)
    @DisplayName("Get another player profile")
    @Severity(CRITICAL)
    @Description(NEGATIVE_TEST)
    void getAnotherPlayerProfileTest() {
        step("Send get another client profile request", () -> {
            responseWrapper = playerService.postGetPlayerProfile(wrongPlayerId);

            step("Check response", () -> {
                assertThat(responseWrapper.getStatusCode())
                        .isEqualTo(SC_NOT_FOUND);
            });
        });
    }

}
