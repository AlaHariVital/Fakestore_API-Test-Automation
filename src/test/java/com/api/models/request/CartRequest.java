package com.api.models.request;

import java.util.ArrayList;
import java.util.List;

/** Payload for creating/updating a cart: userId, date and a list of products. */
public class CartRequest {

    private int userId;
    private String date;
    private List<CartProduct> products;

    public CartRequest() {
    }

    public CartRequest(int userId, String date, List<CartProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartRequest [userId=" + userId + ", date=" + date + ", products=" + products + "]";
    }

    public static class CartRequestBuilder {
        private int userId;
        private String date;
        private List<CartProduct> products = new ArrayList<CartProduct>();

        public CartRequestBuilder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public CartRequestBuilder date(String date) {
            this.date = date;
            return this;
        }

        public CartRequestBuilder products(List<CartProduct> products) {
            this.products = products;
            return this;
        }

        public CartRequestBuilder addProduct(int productId, int quantity) {
            this.products.add(new CartProduct(productId, quantity));
            return this;
        }

        public CartRequest build() {
            return new CartRequest(userId, date, products);
        }
    }
}
