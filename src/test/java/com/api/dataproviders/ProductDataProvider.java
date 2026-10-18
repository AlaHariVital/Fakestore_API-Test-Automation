package com.api.dataproviders;

import org.testng.annotations.DataProvider;

/** Supplies several valid product IDs (1..20 exist on fakestoreapi) for the data-driven cart test. */
public class ProductDataProvider {

    @DataProvider(name = "productIds")
    public Object[][] productIds() {
        return new Object[][]{
                {1},
                {2},
                {3},
                {5},
                {7},
        };
    }
}
