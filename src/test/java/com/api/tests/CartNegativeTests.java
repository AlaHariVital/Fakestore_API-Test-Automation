package com.api.tests;

import com.api.base.CartService;
import com.api.models.request.CartRequest;
import com.api.models.response.CartResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Negative / edge cases. fakestoreapi is a mock, so its "negative" contract is
 * specific: a non-numeric id is a real 400, a missing cart is 200 + null, and
 * the payload is not validated. Each test asserts the API's actual behaviour.
 */
public class CartNegativeTests extends BaseTest {

    @Test(groups = {"cart", "negative"},
            description = "GET /carts/{nonNumericId} returns 400 with an error body")
    public void getCart_nonNumericId_returnsBadRequest() {
        step("GET /carts/abc");
        Response response = new CartService().getCart("abc");

        Assert.assertEquals(response.getStatusCode(), 400, "A non-numeric id should be a 400");
        Assert.assertEquals(response.jsonPath().getString("status"), "error", "Error status expected");
        Assert.assertEquals(response.jsonPath().getString("message"), "cart id should be provided",
                "Error message mismatch");
    }

    @Test(groups = {"cart", "negative"},
            description = "GET /carts/{nonExistentId} returns 200 with a null body (mock behaviour)")
    public void getCart_nonExistentId_returnsNull() {
        step("GET /carts/9999");
        Response response = new CartService().getCart(9999);

        Assert.assertEquals(response.getStatusCode(), 200, "Mock returns 200 even for a missing cart");
        Assert.assertEquals(response.getBody().asString().trim(), "null",
                "A missing cart should come back as a null body");
    }

    @Test(groups = {"cart", "negative"},
            description = "POST /carts accepts a non-existent productId (mock does not validate the payload)")
    public void createCart_withNonExistentProduct_isAcceptedByMock() {
        step("POST /carts referencing a product id that does not exist");
        CartRequest cart = new CartRequest.CartRequestBuilder()
                .userId(1)
                .date("2024-02-01")
                .addProduct(999999, 1)
                .build();
        Response response = new CartService().createCart(cart);

        // Documents a real contract gap: a stricter API should reject this with 4xx.
        Assert.assertEquals(response.getStatusCode(), 201, "Mock still creates the cart");
        CartResponse created = response.as(CartResponse.class);
        Assert.assertEquals(created.getProducts().get(0).getProductId(), 999999,
                "Mock echoes the unknown productId unchanged");
    }
}
