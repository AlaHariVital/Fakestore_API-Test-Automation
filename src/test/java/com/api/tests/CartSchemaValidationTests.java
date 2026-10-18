package com.api.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.api.base.AuthService;
import com.api.base.CartService;
import com.api.base.ProductService;
import com.api.models.request.AuthRequest;
import com.api.models.request.CartRequest;
import com.api.utility.ConfigReader;
import org.testng.annotations.Test;

/**
 * Response schema validation — asserts each response conforms to a committed
 * JSON Schema (src/test/resources/schemas) via REST Assured's matcher.
 */
public class CartSchemaValidationTests extends BaseTest {

    @Test(groups = {"schema"}, description = "GET /carts/{id} response matches the cart schema")
    public void getCart_matchesCartSchema() {
        step("Validate GET /carts/2 against cart-schema.json");
        new CartService().getCart(2).then().assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/cart-schema.json"));
    }

    @Test(groups = {"schema"}, description = "POST /carts response matches the cart schema")
    public void createCart_matchesCartSchema() {
        step("Validate POST /carts against cart-schema.json");
        CartRequest cart = new CartRequest.CartRequestBuilder()
                .userId(1).date("2024-02-01").addProduct(1, 2).build();
        new CartService().createCart(cart).then().assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/cart-schema.json"));
    }

    @Test(groups = {"schema"}, description = "POST /auth/login response matches the auth schema")
    public void login_matchesAuthSchema() {
        step("Validate POST /auth/login against auth-schema.json");
        AuthRequest request = new AuthRequest.AuthRequestBuilder()
                .username(ConfigReader.get("auth.username"))
                .password(ConfigReader.get("auth.password"))
                .build();
        new AuthService().login(request).then().assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/auth-schema.json"));
    }

    @Test(groups = {"schema"}, description = "GET /products/{id} response matches the product schema")
    public void getProduct_matchesProductSchema() {
        step("Validate GET /products/1 against product-schema.json");
        new ProductService().getProduct(1).then().assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-schema.json"));
    }
}
