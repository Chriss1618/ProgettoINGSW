package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct.INDEX_ACTION_EDIT_PRODUCT;
import static com.ratatouille.Controllers.SubControllers.ManagerRequestFactory.INDEX_REQUEST_PRODUCTS;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;

import junit.framework.TestCase;

import org.junit.Assert;

public class RequestProductsTest extends TestCase {
    private RequestProducts editProductAction;

    public void setUp() throws Exception {
        editProductAction = (RequestProducts) new ManagerRequestFactory().requestHandlerMap.get(INDEX_REQUEST_PRODUCTS);
        super.setUp();
    }

    public void testGetProductsFromServerIncorrectRestaurantCorrectCategory() {
        int id_cat = 1;
        int id_restaurant = 12;
        Assert.assertFalse( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }

    public void testGetProductsFromServerCorrectRestaurantCorrectCategory() {
        int id_cat = 113;
        int id_restaurant = 1;
        Assert.assertTrue( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }

    public void testGetProductsFromServerCorrectRestaurantIncorrectCategory() {
        int id_cat = 112;
        int id_restaurant = 1;

        Assert.assertFalse( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }
}