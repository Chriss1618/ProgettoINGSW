package com.ratatouille.Controllers.SubControllers.RequestHandlers;
import static com.ratatouille.Controllers.SubControllers.ManagerRequestFactory.INDEX_REQUEST_PRODUCTS;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestProductsTest {
    private RequestProducts editProductAction;

    @Before
    public void setUp()  {
        editProductAction = (RequestProducts) new ManagerRequestFactory().requestHandlerMap.get(INDEX_REQUEST_PRODUCTS);
    }

    @Test
    public void IncorrectRestaurantCorrectCategory() {
        int id_cat = 1;
        int id_restaurant = 12;
        Assert.assertFalse( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }
    @Test
    public void CorrectRestaurantCorrectCategory() {
        int id_cat = 113;
        int id_restaurant = 1;
        Assert.assertTrue( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }
    @Test
    public void CorrectRestaurantIncorrectCategory() {
        int id_cat = 112;
        int id_restaurant = 1;

        Assert.assertFalse( editProductAction.getProductsFromServer(id_cat,id_restaurant) );

    }
}