package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

public class Fragment_ReportOrder extends Fragment implements ViewLayout {
    private Manager manager;
    public Fragment_ReportOrder() {
    }
    public Fragment_ReportOrder(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__report_order, container, false);
    }

    //DATA
    @Override
    public void PrepareData() {

    }

    //LAYOUT
    @Override
    public void PrepareLayout() {

    }

    @Override
    public void LinkLayout() {

    }
    @Override
    public void SetActionsOfLayout() {

    }
    @Override
    public void SetDataOnLayout() {

    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }
    @Override
    public void EndAnimations() {

    }
}