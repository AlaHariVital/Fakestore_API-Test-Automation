package com.api.tests;

import com.api.base.CartService;
import com.api.models.request.CartRequest;
import com.api.models.response.CartResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Positive Cart CRUD: POST / GET / PUT / DELETE against /carts. */
public class CartCrudTests extends BaseTest {

    private CartRequest sampleCart() {
        return new CartRequest.CartRequestBuilder()
                .userId(1)
                .date("2024-02-01")
                .addProduct(1, 4)
                .addProduct(2, 1)
                .build();
    }

    @Test(groups = {"cart", "positive"},
            description = "POST /carts creates a cart and echoes it back with a new id")
    public void createCart_isCreated() {
        step("POST /carts with two products");
        Response response = new CartService().createCart(sampleCart());

        Assert.assertEquals(response.getStatusCode(), 201, "POST /carts should return 201");
        CartResponse cart = response.as(CartResponse.class);
        Assert.assertTrue(cart.getId() > 0, "A cart id should be assigned");
        Assert.assertEquals(cart.getUserId(), 1, "userId should be echoed");
        Assert.assertEquals(cart.getProducts().size(), 2, "Both products should be echoed");
    }

    @Test(groups = {"cart", "positive"},
            description = "GET /carts/{id} returns the requested cart")
    public void getCartById_returnsCart() {
        step("GET /carts/1");
        Response response = new CartService().getCart(1);

        Assert.assertEquals(response.getStatusCode(), 200, "GET should return 200");
        CartResponse cart = response.as(CartResponse.class);
        Assert.assertEquals(cart.getId(), 1, "Returned cart id should match");
        Assert.assertTrue(cart.getUserId() > 0, "Cart should have a userId");
        Assert.assertFalse(cart.getProducts().isEmpty(), "Cart should contain products");
    }

    @Test(groups = {"cart", "positive"},
            description = "GET /carts returns a non-empty list of carts")
    public void getAllCarts_returnsList() {
        step("GET /carts");
        Response response = new CartService().getAllCarts();

        Assert.assertEquals(response.getStatusCode(), 200, "GET /carts should return 200");
        int count = response.jsonPath().getList("id").size();
        Assert.assertTrue(count > 0, "There should be at least one cart in the list");
    }

    @Test(groups = {"cart", "positive"},
            description = "PUT /carts/{id} updates the cart and echoes the new state")
    public void updateCart_isUpdated() {
        step("PUT /carts/7 with updated data");
        CartRequest updated = new CartRequest.CartRequestBuilder()
                .userId(2)
                .date("2024-02-02")
                .addProduct(3, 5)
                .build();
        Response response = new CartService().updateCart(7, updated);

        Assert.assertEquals(response.getStatusCode(), 200, "PUT should return 200");
        CartResponse cart = response.as(CartResponse.class);
        Assert.assertEquals(cart.getId(), 7, "The path id should be preserved");
        Assert.assertEquals(cart.getUserId(), 2, "Updated userId should be echoed");
        Assert.assertEquals(cart.getProducts().get(0).getProductId(), 3, "Updated product should be echoed");
    }

    @Test(groups = {"cart", "positive"},
            description = "DELETE /carts/{id} removes the cart and echoes the deleted object")
    public void deleteCart_isDeleted() {
        step("DELETE /carts/7");
        Response response = new CartService().deleteCart(7);

        Assert.assertEquals(response.getStatusCode(), 200, "DELETE should return 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), 7, "Deleted cart id should be returned");
    }
}
