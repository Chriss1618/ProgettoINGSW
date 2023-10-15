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

    //Insieme di test Black Box
    @Test
    public void TestLoginEmptyEmailAndPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("", "", ""));
    }

    @Test
    public void TestLoginEmptyEmail(){
        Assert.assertFalse(loginAction.getUserFromServer("", "123123", "123123"));
    }

    @Test
    public void TestLoginEmptyPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("123123", "", "123123"));
    }

    @Test
    public void TestLoginWrongEmailAndPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("Gigiolone", "Pescheria", "123123"));
    }

    @Test
    public void TestLoginWrongEmail(){
        Assert.assertFalse(loginAction.getUserFromServer("1231231", "123123", "123123"));
    }

    @Test
    public void TestLoginWrongPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("123123", "1231231", "123123"));
    }

    @Test
    public void TestLoginCorrectEmailAndEmptyPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("123123", "", "123123"));
    }

    @Test
    public void TestLoginWrongEmailAndEmptyPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("123123", "", "123123"));
    }

    @Test
    public void TestLoginCorrectCredentials(){
        Assert.assertTrue(loginAction.getUserFromServer("123123", "123123", "123123"));
    }

    //Insieme di White Box




    @After
    public void EndTest(){
        Log.d(TAG, "EndTest: Finito");
    }
}
