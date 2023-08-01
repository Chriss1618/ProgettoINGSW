package com.ratatouille.Models.Interfaces;

public interface ViewLayout {
    void PrepareData();
    void PrepareLayout();

    void LinkLayout();
    void SetActionsOfLayout();
    void SetDataOnLayout();

    void StartAnimations();
    void EndAnimations();
}
