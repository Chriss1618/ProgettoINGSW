package com.ratatouille.Models.Interfaces;

public interface ISubController extends IController {
    void changeOnMain(int indexMain, Object msg);
    void goBack(int indexFrom);
}
