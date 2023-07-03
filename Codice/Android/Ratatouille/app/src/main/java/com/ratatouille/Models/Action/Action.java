package com.ratatouille.Models.Action;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Models.SourceInfo;

public class Action {
    private final SourceInfo SourceInfo;
    private final Manager Manager;
    private final Integer actionType;
    private final Object Data;
    private  FunctionCallBackAction functionCallBack = null;
    private  FunctionCallBackAction2 functionCallBack2 = null;
    public interface FunctionCallBackAction{
        void execute();
    }
    public interface FunctionCallBackAction2{
        void execute(Object data);
    }

    public Action(Integer actionType, Object data, Manager manager, SourceInfo SourceInfo) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
        this.SourceInfo = SourceInfo;
    }

    public Action(Integer actionType, Object data, Manager manager, FunctionCallBackAction functionCallBack, SourceInfo SourceInfo) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
        this.functionCallBack = functionCallBack;
        this.SourceInfo = SourceInfo;
    }

    public Action(Integer actionType, Object data, Manager manager,FunctionCallBackAction2 functionCallBack,SourceInfo SourceInfo) {
        this.Manager = manager;
        this.actionType = actionType;
        this.Data = data;
        this.functionCallBack2 = functionCallBack;
        this.SourceInfo = SourceInfo;
    }

    public Integer getActionType() {
        return actionType;
    }
    public Object getData() {
        return Data;
    }
    public Manager getManager() {
        return Manager;
    }
    public SourceInfo getSourceInfo() {
        return SourceInfo;
    }

    public void callBack(){
        if ( this.functionCallBack != null ) this.functionCallBack.execute();
    }
    public void callBack(Object data){
        if ( this.functionCallBack2 != null ) this.functionCallBack2.execute(data);
    }
}
