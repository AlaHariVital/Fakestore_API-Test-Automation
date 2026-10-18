package com.api.tests;

import com.api.base.AuthService;
import com.api.models.request.AuthRequest;
import com.api.models.response.AuthResponse;
import com.api.utility.ConfigReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Authentication: valid credentials return a token; invalid credentials are rejected. */
public class AuthTests extends BaseTest {

    @Test(groups = {"auth", "positive"},
            description = "POST /auth/login with valid credentials returns 201 and a token")
    public void login_validCredentials_returnsToken() {
        step("POST /auth/login with the configured valid account");
        AuthRequest request = new AuthRequest.AuthRequestBuilder()
                .username(ConfigReader.get("auth.username"))
                .password(ConfigReader.get("auth.password"))
                .build();

        Response response = new AuthService().login(request);

        Assert.assertEquals(response.getStatusCode(), 201, "Valid login should return 201");
        AuthResponse auth = response.as(AuthResponse.class);
        Assert.assertNotNull(auth.getToken(), "A token should be returned");
        Assert.assertFalse(auth.getToken().isEmpty(), "The token should not be empty");
    }

    @Test(groups = {"auth", "negative"},
            description = "POST /auth/login with invalid credentials returns 401")
    public void login_invalidCredentials_returnsUnauthorized() {
        step("POST /auth/login with bad credentials");
        AuthRequest request = new AuthRequest.AuthRequestBuilder()
                .username("no_such_user")
                .password("wrong_password")
                .build();

        Response response = new AuthService().login(request);

        Assert.assertEquals(response.getStatusCode(), 401, "Invalid login should return 401");
        Assert.assertTrue(response.getBody().asString().toLowerCase().contains("incorrect"),
                "Body should explain the credentials are incorrect");
    }
}
