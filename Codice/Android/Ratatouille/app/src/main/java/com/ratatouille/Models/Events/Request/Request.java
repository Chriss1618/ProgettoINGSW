package com.ratatouille.Models.Events.Request;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Events.SourceInfo;

public class Request {
    private SourceInfo SourceInfo;
    private final Object      Data;
    private final int         TypeRequest;
    private final FunctionCallBack functionCallBack;
    private Manager Manager;
    public interface FunctionCallBack{
        void execute(Object data);
    }

    public Request(SourceInfo sourceInfo, Object data, int typeRequest, FunctionCallBack functionCallBack) {
        this.SourceInfo = sourceInfo;
        Data = data;
        TypeRequest = typeRequest;
        this.functionCallBack = functionCallBack;
    }
    public void setSourceInfo(com.ratatouille.Models.Events.SourceInfo sourceInfo) {
        SourceInfo = sourceInfo;
    }
    public void setManager(com.ratatouille.Controllers.SubControllers.Manager manager) {
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
