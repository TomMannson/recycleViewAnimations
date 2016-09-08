package com.example.mvp.tomaszkrol.recycle.anim.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mvp.tomaszkrol.recycle.anim.AnimationHandler;

/**
 * Created by tomasz.krol on 2016-09-08.
 */
public class ResizeAnimHandler implements AnimationHandler {

    int heightFrom, heightTo, widthFrom, widthTo;

    public ResizeAnimHandler(int heightFrom, int heightTo, int widthFrom, int widthTo) {
        this.heightFrom = heightFrom;
        this.heightTo = heightTo;
        this.widthFrom = widthFrom;
        this.widthTo = widthTo;
    }

    @Override
    public boolean animShouldPerform() {
        return heightFrom != heightTo || widthFrom != widthTo;
    }

    @Override
    public void performOnStart(View v) {
        if(heightFrom != heightTo){
            v.getLayoutParams().height = heightFrom;
        }

        if(widthFrom != widthTo){
            v.getLayoutParams().width = widthFrom;
        }
    }

    @Override
    public void performAnimationFrame(View v, float time) {
        int h = 0;
        if((heightTo - heightFrom) > 1){
            h = time == 1? RecyclerView.LayoutParams.WRAP_CONTENT :
                    heightFrom + (int) ((heightTo - heightFrom) * time);
            v.getLayoutParams().height = h;
            v.requestLayout();
        }
        else{
            h = heightFrom + (int) ((heightTo - heightFrom) * time);
            v.getLayoutParams().height = h;
            v.requestLayout();
        }
    }
}
