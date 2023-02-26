package com.ratatouille.Interfaces.RecyclerInterfaces;

public class RecycleEventListener {
    public onClickItemAdapterListener AdapterListener;

    public RecycleEventListener() {
        this.AdapterListener = null;
    }

    public void setOnClickItemAdapterListener(onClickItemAdapterListener AdapterListener) {
        this.AdapterListener = AdapterListener;
    }

}
