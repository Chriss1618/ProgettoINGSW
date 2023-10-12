package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory.INDEX_ACTION_ADD_CATEGORY;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InsertCategoryTest {

    private ActionsListCategory.AddNewCategory_ActionHandler addCategoryAction;

    @Before
    public  void  setUp(){
        addCategoryAction = (ActionsListCategory.AddNewCategory_ActionHandler) new ActionsListCategory().actionHandlerMap.get(INDEX_ACTION_ADD_CATEGORY);
    }

    @Test
    public void TestInsertCategoryEmpty(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("Barbabietole", "2"));

    }
}