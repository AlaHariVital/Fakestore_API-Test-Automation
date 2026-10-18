package com.api.constants;

/** Central place for the API paths, so tests/services never hard-code URLs. */
public final class Endpoints {

    private Endpoints() {
    }

    public static final String CARTS = "/carts";
    public static final String PRODUCTS = "/products";
    public static final String AUTH_LOGIN = "/auth/login";

    public static String cartById(int id) {
        return CARTS + "/" + id;
    }

    public static String productById(int id) {
        return PRODUCTS + "/" + id;
    }
}
