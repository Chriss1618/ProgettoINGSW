package com.ratatouille.Interfaces.RecyclerInterfaces;

public class RecycleEventListener {
    public onClickItemAdapterListener AdapterListener;
    public onCheckItemAdapterListener AdapterCheckListener;
    public onClickItemOptionListener AdapterOptionListener;

    public RecycleEventListener() {
        this.AdapterListener        = null;
        this.AdapterOptionListener  = null;
        this.AdapterCheckListener   = null;
    }

    public void setOnClickItemAdapterListener(onClickItemAdapterListener AdapterListener) {
        this.AdapterListener = AdapterListener;
    }

    public void setOnCheckItemAdapterListener(onCheckItemAdapterListener AdapterCheckListener){
        this.AdapterCheckListener   = AdapterCheckListener;
    }

    public void setOnClickItemOptionAdapterListener(onClickItemOptionListener AdapterOptionListener){
        this.AdapterOptionListener = AdapterOptionListener;
    }
}
