package com.api.models.request;

/** A line item in a cart: a product id and its quantity. Used in both requests and responses. */
public class CartProduct {

    private int productId;
    private int quantity;

    public CartProduct() {
    }

    public CartProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProduct [productId=" + productId + ", quantity=" + quantity + "]";
    }

    public static class CartProductBuilder {
        private int productId;
        private int quantity;

        public CartProductBuilder productId(int productId) {
            this.productId = productId;
            return this;
        }

        public CartProductBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartProduct build() {
            return new CartProduct(productId, quantity);
        }
    }
}
