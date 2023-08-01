package com.ratatouille.Models.Interfaces;

import com.ratatouille.Controllers.SubControllers.Manager;

import java.util.Map;

public interface IViewFactory {
    ViewLayout createView(int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException;
}
