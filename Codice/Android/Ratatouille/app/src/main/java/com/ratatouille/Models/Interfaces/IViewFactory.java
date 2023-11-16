package com.ratatouille.Models.Interfaces;

import com.ratatouille.Controllers.SubControllers.Manager;

public interface IViewFactory {
    IViewLayout createView(int typeView, Manager managerFragments )throws IllegalAccessException, InstantiationException;
}
