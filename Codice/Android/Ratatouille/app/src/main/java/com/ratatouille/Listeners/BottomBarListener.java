package com.ratatouille.Listeners;

public class BottomBarListener {
    //INTERFACE
    private interface ShowBottomBarListener {
        void showBottomBar();
    }
    private interface HideBottomBarListener {
        void hideBottomBar();

    }

    private interface EnableBottomBarListener {
        void enableBottomBar();
    }
    private interface DisableBottomBarListener {
        void disableBottomBar();
    }

    //FUNCTIONAL
    private HideBottomBarListener hideBottomBarListener;
    private ShowBottomBarListener showBottomBarListener;

    private EnableBottomBarListener enableBottomBarListener;
    private DisableBottomBarListener disableBottomBarListener;

    public BottomBarListener() {
        this.hideBottomBarListener = null;
        this.showBottomBarListener = null;
        this.enableBottomBarListener = null;
        this.disableBottomBarListener = null;
    }

    //SETTING LISTENERS
    public void setHideBottomBarListener(HideBottomBarListener hideBottomBarListener) {
        this.hideBottomBarListener = hideBottomBarListener;
    }

    public void setShowBottomBarListener(ShowBottomBarListener showBottomBarListener) {
        this.showBottomBarListener = showBottomBarListener;
    }

    public void setEnableBottomBarListener(EnableBottomBarListener enableBottomBarListener) {
        this.enableBottomBarListener = enableBottomBarListener;
    }

    public void setDisableBottomBarListener(DisableBottomBarListener disableBottomBarListener) {
        this.disableBottomBarListener = disableBottomBarListener;
    }

    //Methods
    public void showBottomBar(){
        if(showBottomBarListener != null ){
            showBottomBarListener.showBottomBar();
        }
    }
    public void hideBottomBar(){
        if(hideBottomBarListener != null ){
            hideBottomBarListener.hideBottomBar();
        }
    }
    public void enableBottomBar(){
        if(enableBottomBarListener != null ){
            enableBottomBarListener.enableBottomBar();
        }
    }
    public void disableBottomBar(){
        if(disableBottomBarListener != null ){
            disableBottomBarListener.disableBottomBar();
        }
    }
}
