package com.ratatouille.Models.Events;

public class SourceInfo {
    private int Index_TypeManager;
    private int Index_TypeController;
    private int Index_TypeView;

    public SourceInfo(int Index_TypeManager, Integer Index_TypeController) {
        this.Index_TypeManager = Index_TypeManager;
        this.Index_TypeController = Index_TypeController;
    }

    public int getIndex_TypeManager() {
        return Index_TypeManager;
    }
    public int getIndex_TypeController() {
        return Index_TypeController;
    }
    public int getIndex_TypeView() {
        return Index_TypeView;
    }

    public void setIndex_TypeView(int index_TypeView) {
        Index_TypeView = index_TypeView;
    }
}
