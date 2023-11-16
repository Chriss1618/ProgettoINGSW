package com.ratatouille.Models.Events.Request;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Events.SourceInfo;

public class Request {
    private SourceInfo SourceInfo;
    private Manager Manager;
    private final int         TypeRequest;
    private final Object      Data;
    private final FunctionCallBack functionCallBack;
    public interface FunctionCallBack{
        void execute(Object data);
    }

    public Request(SourceInfo sourceInfo, Object data, int typeRequest, FunctionCallBack functionCallBack) {
        this.SourceInfo = sourceInfo;
        Data = data;
        TypeRequest = typeRequest;
        this.functionCallBack = functionCallBack;
    }
    public void setSourceInfo(SourceInfo sourceInfo) {
        SourceInfo = sourceInfo;
    }
    public void setManager(Manager manager) {
        Manager = manager;
    }
    public Manager getManager() {
        return Manager;
    }

    public SourceInfo getSourceInfo() {
        return SourceInfo;
    }
    public int getTypeRequest() {
        return TypeRequest;
    }
    public Object getData() {
        return Data;
    }

    public void callBack(Object data){
        if ( this.functionCallBack != null ) this.functionCallBack.execute(data);
    }
}
