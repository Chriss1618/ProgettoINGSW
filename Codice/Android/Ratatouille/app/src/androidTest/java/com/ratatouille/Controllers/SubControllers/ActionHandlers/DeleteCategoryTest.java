package com.ratatouille.Controllers.SubControllers.ActionHandlers;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_ADD_CATEGORY;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_REMOVE_CATEGORY;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteCategoryTest {
    private static final String TAG = "DeleteCategoryTest";
    private ActionsListCategory.AddNewCategory_ActionHandler addCategoryAction;
    private ActionsListCategory.DeleteCategory_ActionHandler deleteCategoryAction;

    @Before
    public  void  setUp(){
        addCategoryAction = (ActionsListCategory.AddNewCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_ADD_CATEGORY);
        deleteCategoryAction = (ActionsListCategory.DeleteCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_REMOVE_CATEGORY);
    }

    @Test
    public void CorrectCategoryAndCorrectRestaurantId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertTrue(deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest));
    }

    @Test
    public void CorrectCategoryAndIncorrectRestaurantId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertFalse(deleteCategoryAction.sendDeleteCategoryToServer(idCat, 123123));
        deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest);
    }

    @Test
    public void IncorrectCategoryAndCorrectRestaurantId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertFalse(deleteCategoryAction.sendDeleteCategoryToServer(123123, id_rest));
        deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest);
    }

    @Test
    public void IncorrectCategoryAndIncorrectRestaurantId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertFalse(deleteCategoryAction.sendDeleteCategoryToServer(123123, 123123));
        deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest);
    }

    //Insieme di test WhiteBox
    @Test
    public void DeleteCategoryTestPath_144_145(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertTrue(deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest));
    }
}
