package com.testslotegrator.services.rest.common;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.io.IoBuilder;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.testslotegrator.config.Configuration.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseService<T extends BaseService> {

    protected final String authHeaderName = "Authorization";

    private String bearerToken;

    public BaseService(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    private final Supplier<RequestSpecBuilder> requestSpecBuilderSupplier = () -> {
        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(JSON)
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .addFilter(RequestLoggingFilter.logRequestTo(IoBuilder.forLogger().buildPrintStream()))
                .addFilter(ResponseLoggingFilter.logResponseTo(IoBuilder.forLogger().buildPrintStream()));
        handleOptionalParams(rsb);
        return rsb;
    };

    private RequestSpecBuilder modifiedReqSpecBuilder;

    private final Supplier<ResponseSpecBuilder> responseSpecBuilder = () -> new ResponseSpecBuilder()
            .log(LogDetail.ALL);

    private void handleOptionalParams(RequestSpecBuilder rsb) {
        if (Optional.ofNullable(bearerToken).isPresent()) {
            rsb.addHeader(authHeaderName, "Bearer %s".formatted(bearerToken));
        }
    }

    protected ResponseWrapper wrap(ValidatableResponse response) {
        return new ResponseWrapper(response);
    }

    protected ValidatableResponse sendRequest(Method method, String path) {
        ValidatableResponse response = given(this.getRequestSpecification(), this.getResponseSpecification())
                .request(method, path)
                .then().log().all();
        log.info(format("Finished request [%s] %s within %s ms", method.name(), path, response.extract().time()));
        modifiedReqSpecBuilder = null;
        return response;
    }

    public RequestSpecification getRequestSpecification() {
        return (Optional.ofNullable(modifiedReqSpecBuilder).isPresent() ? modifiedReqSpecBuilder : requestSpecBuilderSupplier.get())
                .build();
    }

    public ResponseSpecification getResponseSpecification() {
        return getResponseSpecBuilder().get()
                .build();
    }


    public T withChangesToRequestSpec(Function<RequestSpecBuilder, RequestSpecBuilder> specBuilderFunction) {
        modifiedReqSpecBuilder = specBuilderFunction.apply(requestSpecBuilderSupplier.get());
        return (T) this;
    }

}
