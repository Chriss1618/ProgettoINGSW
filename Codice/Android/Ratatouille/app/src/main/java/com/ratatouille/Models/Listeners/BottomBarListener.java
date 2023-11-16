package com.ratatouille.Models.Listeners;

public class BottomBarListener {
    //INTERFACE
    public interface ShowBottomBarListener {
        void showBottomBar();
    }
    public interface HideBottomBarListener {
        void hideBottomBar();

    }

    //DATA
    public boolean visible = true;

    //FUNCTIONAL
    private HideBottomBarListener hideBottomBarListener;
    private ShowBottomBarListener showBottomBarListener;

    public BottomBarListener() {
        this.hideBottomBarListener = null;
        this.showBottomBarListener = null;
    }

    //SETTING LISTENERS
    public void setHideBottomBarListener(HideBottomBarListener hideBottomBarListener) {
        this.hideBottomBarListener = hideBottomBarListener;
    }
    public void setShowBottomBarListener(ShowBottomBarListener showBottomBarListener) {
        this.showBottomBarListener = showBottomBarListener;
    }

    //Methods
    public void showBottomBar(){
        if(showBottomBarListener != null ){
            showBottomBarListener.showBottomBar();
            visible = true;
        }
    }
    public void hideBottomBar(){
        if(hideBottomBarListener != null ){
            hideBottomBarListener.hideBottomBar();
            visible = false;
        }
    }
}
