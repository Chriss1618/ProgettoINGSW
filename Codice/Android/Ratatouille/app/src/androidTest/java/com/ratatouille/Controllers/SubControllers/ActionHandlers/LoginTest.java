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

    @Test
    public void TestLoginEmailPasswordEmpty(){

        Assert.assertFalse(loginAction.getUserFromServer("", "", ""));

    }

    @Test
    public void TestLoginEmailPasswordCorrect(){

        Assert.assertTrue(loginAction.getUserFromServer("asdasd", "asdasd", "123123"));

    }

    @After
    public void EndTest(){

        Log.d(TAG, "EndTest: Finito");
    }
}
