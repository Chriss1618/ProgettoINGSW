package com.ratatouille.Models.Request;

import com.ratatouille.Models.SourceInfo;

public class Request {
    private final SourceInfo  sourceInfo;
    private final Object      Data;
    private final int         TypeRequest;
    private final FunctionCallBack functionCallBack;

    public interface FunctionCallBack{
        void execute(Object data);
    }

    public Request(SourceInfo sourceInfo, Object data, int typeRequest, FunctionCallBack functionCallBack) {
        this.sourceInfo = sourceInfo;
        Data = data;
        TypeRequest = typeRequest;
        this.functionCallBack = functionCallBack;
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
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
