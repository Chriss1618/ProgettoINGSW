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
    public void CorrectProductCorrectProductId(){

        Product newProd = new Product();
        newProd.setID_product(1);
        newProd.setID_category(124);
        newProd.setNameProduct("Prova");
        newProd.setPriceProduct(1);
        newProd.setDescriptionProduct("è una prova");
        newProd.setAllergeniProduct("è na prov");





        Assert.assertTrue(editProductAction.sendUpdatedProductToServer(newProd,"35"));

    }

    @Test
    public void CorrectProductIncorrectProductId(){

        Product newProd = new Product();
        newProd.setID_product(1);
        newProd.setID_category(124);
        newProd.setNameProduct("Prova");
        newProd.setPriceProduct(1);
        newProd.setDescriptionProduct("è una prova");
        newProd.setAllergeniProduct("è na prov");


        Assert.assertFalse(editProductAction.sendUpdatedProductToServer(newProd,""));

    }

    @Test
    public void IncorrectProductCorrectProductId(){

        Product newProd = new Product();

        Assert.assertFalse(editProductAction.sendUpdatedProductToServer(newProd,"35"));

    }

    @Test
    public void IncorrectProductIncorrectProductId(){

        Product newProd = new Product();

        Assert.assertFalse(editProductAction.sendUpdatedProductToServer(newProd,""));

    }
}
