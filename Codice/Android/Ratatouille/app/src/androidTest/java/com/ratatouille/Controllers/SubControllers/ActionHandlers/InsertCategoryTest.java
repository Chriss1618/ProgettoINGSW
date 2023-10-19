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

    //Insieme di Test BlackBox
    @Test
    public void CorrectCategoryAndCorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", "123"));
        //cancellare la categoria inserita
    }

    @Test
    public void CorrectCategoryAndIncorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", "IdPresenteNelDB"));
    }

    @Test
    public void CorrectCategoryAndEmptyId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaNonPresenteNelDB", ""));
    }

    @Test
    public void IncorrectCategoryAndCorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaPresenteNelDB", "123"));
    }

    @Test
    public void IncorrectCategoryAndIncorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaPresenteNelDB", "IdPresenteNelDB"));
    }

    @Test
    public void IncorrectCategoryAndEmptyId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("CategoriaPresenteNelDB", ""));
    }

    @Test
    public void EmptyCategoryAndCorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", "123"));
    }

    @Test
    public void EmptyCategoryAndIncorrectId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", "IdPresenteNelDB"));
    }

    @Test
    public void EmptyCategoryAndEmptyId(){
        Assert.assertEquals(0,addCategoryAction.sendNewCategoryToServer("", ""));
    }
}