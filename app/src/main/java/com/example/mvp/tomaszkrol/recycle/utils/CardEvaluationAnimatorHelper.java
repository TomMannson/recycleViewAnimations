package com.example.mvp.tomaszkrol.recycle.utils;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by tomasz.krol on 2016-09-08.
 */
public class CardEvaluationAnimatorHelper {

    public static float getCardEvaluation(CardView view) {

        final float evaluation = view.getCardElevation();

        return evaluation;
    }


    public static void animateChangeSizeOfView(final View v, final float evaluationFrom, final float evaluationTo) {

        final CardView card;
        if (v instanceof CardView && evaluationFrom != evaluationTo) {
            card = (CardView) v;
            card.setCardElevation(evaluationFrom);
//            v.requestLayout();

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    float evaluation = 0;
                    evaluation = evaluationFrom + (int) ((evaluationTo - evaluationFrom) * interpolatedTime);
                    card.setCardElevation(evaluation);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return false;
                }
            };

            a.setDuration(500);
            v.startAnimation(a);
        }
    }
}
