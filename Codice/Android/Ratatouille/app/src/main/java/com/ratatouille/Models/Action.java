package com.ratatouille.Models;

import com.ratatouille.Interfaces.SubController;

public class Action {
    private SubController Manager;
    private Integer actionType;
    private Object Data;
    private functionCallBack functionCallBack = null;


    public interface functionCallBack{
        void execute();
    }


    public Action(Integer actionType, Object data, SubController manager) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
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

    public void setCallBack(functionCallBack functionCallBack){
        this.functionCallBack = functionCallBack;
    }

    public void callBack(){
        if ( this.functionCallBack != null ) this.functionCallBack.execute();
    }
}
