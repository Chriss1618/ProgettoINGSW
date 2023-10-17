package com.ratatouille.Controllers.SubControllers.ActionHandlers;


import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin.INDEX_ACTION_LOGIN;

import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    private static final String TAG = "LoginTest";
    private ActionsLogin.Login_ActionHandler loginAction;

    @Before
    public  void  setUp(){
        loginAction = (ActionsLogin.Login_ActionHandler) new ActionsLogin().actionHandlerMap.get(INDEX_ACTION_LOGIN);

    }

    //Insieme di test BlackBox
    @Test
    public void IncorrectCredentials(){
        Assert.assertFalse(loginAction.getUserFromServer("NonSonoNelDB", "NonSonoNelDB123", "123123"));
    }

    @Test
    public void CorrectCredentials(){
        Assert.assertTrue(loginAction.getUserFromServer("SononelDB@example.com", "SonoNelDB123", ""));
    }


    @Test
    public void EmptyEmailAndPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("", "", ""));
    }

    @Test
    public void EmptyEmail(){
        Assert.assertFalse(loginAction.getUserFromServer("", "SonoNelDB123", "123123"));
    }

    @Test
    public void EmptyPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("SononelDB@example.com", "", "123123"));
    }

    @Test
    public void IncorrectEmail(){
        Assert.assertFalse(loginAction.getUserFromServer("NonSonoNelDB", "SonoNelDB123", "123123"));
    }

    @Test
    public void IncorrectPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("SononelDB@example.com", "1231231", "123123"));
    }

    @Test
    public void IncorrectEmailAndEmptyPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("123123", "", "123123"));
    }

    //Insieme di test WhiteBox


    @After
    public void EndTest(){
        Log.d(TAG, "EndTest: Finito");
    }
}
