package com.ratatouille.Interfaces.RecyclerInterfaces;

public class RecycleEventListener {
    public onClickItemAdapterListener AdapterListener;
    public onCheckItemAdapterListener AdapterCheckListener;
    public RecycleEventListener() {
        this.AdapterListener = null;
        AdapterCheckListener = null;
    }

    public void setOnClickItemAdapterListener(onClickItemAdapterListener AdapterListener) {
        this.AdapterListener = AdapterListener;
    }

    public void setOnCheckItemAdapterListener(onCheckItemAdapterListener AdapterCheckListener){
        this.AdapterCheckListener   = AdapterCheckListener;
    }
}
