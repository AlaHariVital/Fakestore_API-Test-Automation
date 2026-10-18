package com.api.base;

import static io.restassured.RestAssured.given;

import com.api.filters.LoggingFilter;
import com.api.utility.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base of every service: builds the request specification (base URI + JSON),
 * registers the logging filter once, and exposes thin POST/GET/PUT/DELETE
 * helpers that the concrete services reuse.
 */
public class BaseService {

    private static final String BASE_URI = ConfigReader.get("base.uri");

    private final RequestSpecification requestSpecification;

    static {
        // The public demo API is HTTPS; behind a TLS-intercepting corporate proxy the
        // default trust store may reject the cert, so relax validation.
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new LoggingFilter());
    }

    public BaseService() {
        requestSpecification = given().baseUri(BASE_URI);
    }

    protected void setAuthToken(String token) {
        requestSpecification.header("Authorization", "Bearer " + token);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected Response getRequest(String endpoint) {
        return requestSpecification.get(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint) {
        return requestSpecification.contentType(ContentType.JSON).body(payload).put(endpoint);
    }

    protected Response deleteRequest(String endpoint) {
        return requestSpecification.delete(endpoint);
    }
}
