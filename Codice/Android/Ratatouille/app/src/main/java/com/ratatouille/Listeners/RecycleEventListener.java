package com.ratatouille.Listeners;

public class RecycleEventListener {
    //INTERFACE
    private interface onClickItemAdapterListener {
        void onClickItem(String ItemName);
    }
    private interface onCheckItemAdapterListener {
        void onCheckItem(String ItemName, Boolean flag);
    }
    private interface onClickItemOptionListener {
        void onClickItemOption(String ItemNameOption,int action);
    }

    //FUNCTIONAL
    private onClickItemAdapterListener AdapterListener;
    private onCheckItemAdapterListener AdapterCheckListener;
    private onClickItemOptionListener AdapterOptionListener;

    public RecycleEventListener() {
        this.AdapterListener        = null;
        this.AdapterOptionListener  = null;
        this.AdapterCheckListener   = null;
    }

    //SETTING LISTENERS
    public void setOnClickItemAdapterListener(onClickItemAdapterListener AdapterListener) {
        this.AdapterListener = AdapterListener;
    }

    public void setOnCheckItemAdapterListener(onCheckItemAdapterListener AdapterCheckListener){
        this.AdapterCheckListener   = AdapterCheckListener;
    }

    public void setOnClickItemOptionAdapterListener(onClickItemOptionListener AdapterOptionListener){
        this.AdapterOptionListener = AdapterOptionListener;
    }

    //Methods

}
