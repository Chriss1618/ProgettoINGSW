package com.ratatouille.Controllers.SubControllers.ActionHandlers;


import static com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin.INDEX_ACTION_LOGIN;

import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
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
    public void CorrectCredentials(){
        Assert.assertTrue(loginAction.getUserFromServer("SononelDB@example.com", "SonoNelDB123", ""));
    }

    @Test
    public void CorrectEmailAndIncorrectPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("SonoNelDB@example.com", "NonSonoNelDB123", ""));
    }

    @Test
    public void IncorrectEmailAndCorrectPassword(){
        Assert.assertFalse(loginAction.getUserFromServer("NonSonoNelDB", "SonoNelDB123", ""));
    }

    @Test
    public void IncorrectCredentials(){
        Assert.assertFalse(loginAction.getUserFromServer("NonSonoNelDB", "NonSonoNelDB123", ""));
    }

    //Insieme di test WhiteBox
    @Test
    public void LoginTestPath_164_165_166_167(){
        Assert.assertTrue( loginAction.getUserFromServer("SonoNelDB@example.com", "SonoNelDB123", "") );
    }

    @Test
    public void LoginTestPath_164_165_169_170(){
        Assert.assertFalse( loginAction.getUserFromServer("", "", "") );
    }

    @After
    public void EndTest(){
        Log.d(TAG, "EndTest: Finito");
    }
}
