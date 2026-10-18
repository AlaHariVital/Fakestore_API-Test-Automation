package com.api.base;

import com.api.constants.Endpoints;
import com.api.models.request.CartRequest;

import io.restassured.response.Response;

/** Cart CRUD operations against /carts. */
public class CartService extends BaseService {

    public Response createCart(CartRequest payload) {
        return postRequest(payload, Endpoints.CARTS);
    }

    public Response getCart(int id) {
        return getRequest(Endpoints.cartById(id));
    }

    /** Raw-id variant for negative tests (e.g. a non-numeric id). */
    public Response getCart(String id) {
        return getRequest(Endpoints.CARTS + "/" + id);
    }

    public Response getAllCarts() {
        return getRequest(Endpoints.CARTS);
    }

    public Response updateCart(int id, CartRequest payload) {
        return putRequest(payload, Endpoints.cartById(id));
    }

    public Response deleteCart(int id) {
        return deleteRequest(Endpoints.cartById(id));
    }
}
