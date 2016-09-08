package com.example.mvp.tomaszkrol.recycle.anim.impl;

import android.support.v7.widget.CardView;
import android.view.View;

import com.example.mvp.tomaszkrol.recycle.anim.AnimationHandler;

/**
 * Created by tomasz.krol on 2016-09-08.
 */
public class EvaluationChangeAnimHandler implements AnimationHandler {

    private float evaluationFrom, evaluationTo;

    public EvaluationChangeAnimHandler(float evaluationFrom, float evaluationTo) {
        this.evaluationFrom = evaluationFrom;
        this.evaluationTo = evaluationTo;
    }

    @Override
    public boolean animShouldPerform() {
        return evaluationFrom != evaluationTo;
    }

    @Override
    public void performOnStart(View v) {
        if (v instanceof CardView) {
            CardView card = (CardView) v;
            card.setCardElevation(evaluationFrom);
        }
    }


    @Override
    public void performAnimationFrame(View v, float time) {
        if (v instanceof CardView) {
            float evaluation = 0;
            CardView card = (CardView) v;
            evaluation = evaluationFrom + (int) ((evaluationTo - evaluationFrom) * time);
            card.setCardElevation(evaluation);
        }
    }
}
