package com.api.tests;

import com.api.base.CartService;
import com.api.dataproviders.ProductDataProvider;
import com.api.models.request.CartRequest;
import com.api.models.response.CartResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Data-driven: the same create-cart scenario is run over several product IDs. */
public class CartDataDrivenTests extends BaseTest {

    @Test(groups = {"cart", "data-driven"},
            dataProviderClass = ProductDataProvider.class,
            dataProvider = "productIds",
            description = "POST /carts creates a cart for each provided product id")
    public void createCart_forEachProduct(int productId) {
        step("POST /carts containing productId " + productId);
        CartRequest cart = new CartRequest.CartRequestBuilder()
                .userId(1)
                .date("2024-03-01")
                .addProduct(productId, 2)
                .build();

        Response response = new CartService().createCart(cart);

        Assert.assertEquals(response.getStatusCode(), 201, "Cart should be created for product " + productId);
        CartResponse created = response.as(CartResponse.class);
        Assert.assertEquals(created.getProducts().get(0).getProductId(), productId,
                "Echoed productId should match the input");
        Assert.assertEquals(created.getProducts().get(0).getQuantity(), 2,
                "Echoed quantity should match the input");
    }
}
