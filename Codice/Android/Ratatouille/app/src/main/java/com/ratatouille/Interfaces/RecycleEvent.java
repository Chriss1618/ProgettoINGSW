package com.ratatouille.Interfaces;

public class RecycleEvent {
    public AdapterEvent AdapterListener;
    public RecycleEvent() {
        this.AdapterListener = null;
    }
    public void setOnClickItemAdapterListener(AdapterEvent AdapterListener) {
        this.AdapterListener = AdapterListener;
    }

}
