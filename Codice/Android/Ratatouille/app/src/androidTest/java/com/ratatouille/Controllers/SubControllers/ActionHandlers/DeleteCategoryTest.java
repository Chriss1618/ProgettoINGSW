package com.ratatouille.Controllers.SubControllers.ActionHandlers;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_ADD_CATEGORY;
import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_REMOVE_CATEGORY;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteCategoryTest {

    private ActionsListCategory.AddNewCategory_ActionHandler addCategoryAction;
    private ActionsListCategory.DeleteCategory_ActionHandler deleteCategoryAction;

    @Before
    public  void  setUp(){
        addCategoryAction = (ActionsListCategory.AddNewCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_ADD_CATEGORY);
        deleteCategoryAction = (ActionsListCategory.DeleteCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_REMOVE_CATEGORY);
    }

    /*@Test
    public void CorrectCategoryAndCorrectId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertTrue(deleteCategoryAction.sendDeleteCategoryToServer(idCat, id_rest));
    }*/

    /*@Test
    public void CorrectCategoryAndCorrectId(){
        String nameCategory = "CategoriaDaEliminare";
        int id_rest = 1;
        int idCat = addCategoryAction.sendNewCategoryToServer(nameCategory, id_rest+"");

        Assert.assertTrue(deleteCategoryAction.sendDeleteCategoryToServer(idCat, 123123));
    }*/
}
