package com.ratatouille.Controllers.SubControllers.RequestHandlers;
import static com.ratatouille.Controllers.SubControllers.ManagerRequestFactory.INDEX_REQUEST_PRODUCTS;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewProduct;
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
    public void CorrectRestaurantCorrectCategory() {
        int id_Exist_Restaurant = 1;
        int id_Exist_Category = 281;
        Assert.assertTrue( editProductAction.getProductsFromServer(id_Exist_Category, id_Exist_Restaurant));
    }

    @Test
    public void CorrectRestaurantIncorrectCategory() {
        int id_Exist_Restaurant = 1;
        int id_Not_Exist_Category = 123123;
        Assert.assertFalse( editProductAction.getProductsFromServer(id_Not_Exist_Category ,id_Exist_Restaurant));
    }

    @Test
    public void IncorrectRestaurantCorrectCategory() {
        int id_Not_Exist_Restaurant = 123123;
        int id_Exist_Category = 281;
        Assert.assertFalse( editProductAction.getProductsFromServer(id_Exist_Category, id_Not_Exist_Restaurant) );
    }


    @Test
    public void IncorrectRestaurantIncorrectCategory() {
        int id_Not_Exist_Restaurant = 123123;
        int id_Not_Exist_Category = 123123;
        Assert.assertFalse( editProductAction.getProductsFromServer(id_Not_Exist_Category,id_Not_Exist_Restaurant));
    }

    //Insieme di test WhiteBox
    @Test
    public void RequestProductTestPath_54_55_56_57() {
        int id_Exist_Restaurant = 1;
        int id_Exist_Category = 281;
        Assert.assertTrue( editProductAction.getProductsFromServer(id_Exist_Category, id_Exist_Restaurant));
    }

    @Test
    public void RequestProductTestPath_54_55_59() {
        Assert.assertFalse( editProductAction.getProductsFromServer(0,0));
    }
}