package com.ratatouille.Controllers.Adapters;

public interface ITouchAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemSwap(int position);
}
