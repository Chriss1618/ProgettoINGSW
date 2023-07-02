package com.ratatouille.Interfaces;

import com.ratatouille.Manager;

public interface IViewFactory {
    //static ViewLayout createView(int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException;

    ViewLayout createView(int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException;
}
