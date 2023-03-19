package com.ratatouille.Interfaces.BottomBarInterfaces;

public class BottomBarListener {
    public hideBottomBarLinstener hideBottomBarLinstener;
    public showBottomBarLinstener showBottomBarLinstener;

    public BottomBarListener() {
        this.hideBottomBarLinstener = null;
        this.showBottomBarLinstener = null;
    }

    public void hideBottomBarListener(hideBottomBarLinstener hideBottomBarLinstener) {
        this.hideBottomBarLinstener = hideBottomBarLinstener;
    }

    public void showBottomBarLinstener(showBottomBarLinstener showBottomBarLinstener) {
        this.showBottomBarLinstener = showBottomBarLinstener;
    }
}
