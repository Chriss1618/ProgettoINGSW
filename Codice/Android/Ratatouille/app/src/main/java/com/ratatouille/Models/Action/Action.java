package com.ratatouille.Models.Action;

import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Models.SourceInfo;

public class Action {
    private final SourceInfo SourceInfo;
    private final SubController Manager;
    private final Integer actionType;
    private final Object Data;
    private final FunctionCallBack functionCallBack;

    public interface FunctionCallBack{
        void execute();
    }

    public Action(Integer actionType, Object data, SubController manager,FunctionCallBack functionCallBack,SourceInfo SourceInfo) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
        this.functionCallBack = functionCallBack;
        this.SourceInfo = SourceInfo;
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
    public SourceInfo getSourceInfo() {
        return SourceInfo;
    }

    public void callBack(){
        if ( this.functionCallBack != null ) this.functionCallBack.execute();
    }
}
