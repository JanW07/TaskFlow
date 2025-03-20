package com.taskflow.taskflowbackend.helper;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public interface AuthorizedUserTest {
    default RequestSpecification whenAnonymousWithBody(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().ifValidationFails()
                .when();
    }
    default RequestSpecification whenAnonymous() {
        return given()
                .contentType(ContentType.JSON)
                .log().ifValidationFails()
                .when();
    }

    default RequestSpecification whenAuthenticatedWithBody(Object body, String jwtToken) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .log().ifValidationFails()
                .when();
    }

    default RequestSpecification whenAuthenticated(String jwtToken) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .log().ifValidationFails()
                .when();
    }
}
