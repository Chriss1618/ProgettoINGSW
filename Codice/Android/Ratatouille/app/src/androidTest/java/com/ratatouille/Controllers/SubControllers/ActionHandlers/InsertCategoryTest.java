package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_ADD_CATEGORY;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_REMOVE_CATEGORY;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InsertCategoryTest {
    private static final String TAG = "InsertCategoryTest";
    private ActionsListCategory.AddNewCategory_ActionHandler addCategoryAction;
    private ActionsListCategory.DeleteCategory_ActionHandler deleteCategoryAction;
    int newCategory;

    @Before
    public  void  setUp(){
        addCategoryAction = (ActionsListCategory.AddNewCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_ADD_CATEGORY);
        deleteCategoryAction = (ActionsListCategory.DeleteCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_REMOVE_CATEGORY);
    }

    //Insieme di Test BlackBox
    @Test
    public void CorrectCategoryAndCorrectRestaurantId(){
        newCategory = addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", "1");
        Assert.assertNotEquals(0,newCategory);
        deleteCategoryAction.sendDeleteCategoryToServer(newCategory, 1);
    }

    @Test
    public void CorrectCategoryAndIncorrectRestaurantId(){
        Assert.assertEquals(0, addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", "IdNonPresenteNelDB"));
    }


    @Test
    public void IncorrectCategoryAndCorrectRestaurantId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaPresenteNelDB", "1"));
    }

    @Test
    public void IncorrectCategoryAndIncorrectRestaurantId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaPresenteNelDB", "IdNonPresenteNelDB"));
    }


    @Test
    public void EmptyCategoryAndCorrectRestaurantId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", "1"));
    }

    @Test
    public void EmptyCategoryAndIncorrectRestaurantId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", "IdNonPresenteNelDB"));
    }

    //Insieme di test WhiteBox
    @Test
    public void InsertCategoryTestPath_99_100_101_102(){
        newCategory = addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", "1");
        Assert.assertNotEquals(0,newCategory);
        deleteCategoryAction.sendDeleteCategoryToServer(newCategory, 1);
    }

    @Test
    public void InsertCategoryTestPath_99_100_104(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", ""));
    }

}