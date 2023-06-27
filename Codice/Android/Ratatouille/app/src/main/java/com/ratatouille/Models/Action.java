package com.ratatouille.Models;

import com.ratatouille.Interfaces.SubController;

public class Action {
    private SubController Manager;
    private Integer actionType;
    private Object Data;
    private FunctionCallBack functionCallBack;

    public interface FunctionCallBack{
        void execute();
    }


    public Action(Integer actionType, Object data, SubController manager,FunctionCallBack functionCallBack) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
        this.functionCallBack = functionCallBack;
    }

    public Integer getActionType() {
        return actionType;
    }

    public Object getData() {
        return Data;
    }

    public SubController getManager() {
        return Manager;
    }

    public void callBack(){
        if ( this.functionCallBack != null ) this.functionCallBack.execute();
    }
}
