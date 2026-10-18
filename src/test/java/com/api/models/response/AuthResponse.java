package com.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Deserialized response of POST /auth/login. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
