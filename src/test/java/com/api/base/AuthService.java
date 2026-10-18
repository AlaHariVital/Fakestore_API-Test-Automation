package com.api.base;

import com.api.constants.Endpoints;
import com.api.models.request.AuthRequest;

import io.restassured.response.Response;

/** Authentication against /auth/login. */
public class AuthService extends BaseService {

    public Response login(AuthRequest payload) {
        return postRequest(payload, Endpoints.AUTH_LOGIN);
    }
}
