package com.ratatouille.Models.Interfaces;

public interface IViewLayout {
    void PrepareData();
    void PrepareLayout();

    void LinkLayout();
    void SetActionsOfLayout();
    void SetDataOnLayout();

    void StartAnimations();
    void EndAnimations();
}
