package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct.INDEX_ACTION_EDIT_PRODUCT;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_ADD_CATEGORY;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ratatouille.Models.Entity.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

public class EditProductTest {

    private ActionsInfoEditProduct.EditProduct_ActionHandler editProductAction;

    @Before
    public  void  setUp(){
        editProductAction = (ActionsInfoEditProduct.EditProduct_ActionHandler) new ActionsInfoEditProduct().actionHandlerMap.get(INDEX_ACTION_EDIT_PRODUCT);
    }

    @Test
    public void TestEmptyProduct(){

        Product newProd = new Product();

        Assert.assertFalse(editProductAction.sendUpdatedProductToServer(newProd,"1"));

    }

}
