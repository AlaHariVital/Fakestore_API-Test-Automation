package com.api.base;

import com.api.constants.Endpoints;

import io.restassured.response.Response;

/** Read-only product lookups against /products (used by the data-driven cart test). */
public class ProductService extends BaseService {

    public Response getProduct(int id) {
        return getRequest(Endpoints.productById(id));
    }

    public Response getAllProducts() {
        return getRequest(Endpoints.PRODUCTS);
    }
}
