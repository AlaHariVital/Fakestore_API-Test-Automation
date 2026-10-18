package com.api.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.api.base.CartService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Contract / snapshot test (senior bonus).
 * <p>
 * {@code schemas/cart-schema.json} is a committed snapshot of the cart response
 * shape. These tests assert that live responses still conform to it, so any
 * future drift (a renamed/removed/retyped field) fails the build.
 */
public class CartContractSnapshotTest extends BaseTest {

    @Test(groups = {"contract"},
            description = "GET /carts/{id} conforms to the committed cart contract (schema snapshot)")
    public void cartResponseConformsToContract() {
        step("Assert GET /carts/2 conforms to schemas/cart-schema.json");
        new CartService().getCart(2).then().assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/cart-schema.json"));
    }

    @Test(groups = {"contract"},
            description = "The cart contract keeps its top-level fields: id, userId, date, products")
    public void cartContractKeepsTopLevelFields() {
        step("Assert the cart response still exposes the expected top-level fields");
        Response response = new CartService().getCart(1);
        Assert.assertEquals(response.getStatusCode(), 200);

        Map<String, Object> cart = response.jsonPath().getMap("$");
        Assert.assertTrue(cart.containsKey("id"), "contract field 'id' is missing");
        Assert.assertTrue(cart.containsKey("userId"), "contract field 'userId' is missing");
        Assert.assertTrue(cart.containsKey("date"), "contract field 'date' is missing");
        Assert.assertTrue(cart.containsKey("products"), "contract field 'products' is missing");
    }
}
