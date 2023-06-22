package com.ratatouille.Listeners;

public class RecycleEventListener {
    //INTERFACE
    public interface onClickItemAdapterListener {
        void onClickItem(String ItemName);
    }
    public interface onCheckItemAdapterListener {
        void onCheckItem(String ItemName, Boolean flag);
    }
    public interface onClickItemOptionListener {
        void onClickItemOption(String ItemNameOption,int action);
    }

    //FUNCTIONAL
    private onClickItemAdapterListener  AdapterListener;
    private onCheckItemAdapterListener  AdapterCheckListener;
    private onClickItemOptionListener   AdapterOptionListener;

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
    public void onClickItem(String ItemName){
        if(AdapterListener != null ){
            AdapterListener.onClickItem(ItemName);
        }
    }
    public void onCheckItem(String ItemName, Boolean flag){
        if(AdapterCheckListener != null ){
            AdapterCheckListener.onCheckItem(ItemName,flag);
        }
    }
    public void onClickItemOption(String ItemNameOption,int action){
        if(AdapterOptionListener != null ){
            AdapterOptionListener.onClickItemOption(ItemNameOption,action);
        }
    }
}
