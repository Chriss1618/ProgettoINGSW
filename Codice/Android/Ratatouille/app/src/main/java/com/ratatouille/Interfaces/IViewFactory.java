package com.ratatouille.Interfaces;

import com.ratatouille.Controllers.SubControllers.Manager;

public interface IViewFactory {
    ViewLayout createView(int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException;
}
