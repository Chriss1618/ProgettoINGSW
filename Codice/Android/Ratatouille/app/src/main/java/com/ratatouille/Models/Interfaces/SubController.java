package com.ratatouille.Models.Interfaces;

public interface SubController {
    void showMain();
    void changeOnMain(int indexMain, Object msg);
    void closeView();
    void goBack();
}
