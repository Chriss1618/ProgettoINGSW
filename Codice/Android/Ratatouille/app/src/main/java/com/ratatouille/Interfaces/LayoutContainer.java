package com.ratatouille.Interfaces;

public interface LayoutContainer {
    void PrepareData();
    void PrepareLayout();

    void LinkLayout();
    void SetActionsOfLayout();
    void SetDataOnLayout();

    void StartAnimations();
    void EndAnimations();
}
