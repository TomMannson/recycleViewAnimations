package com.example.mvp.tomaszkrol.recycle.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by tomasz.krol on 2016-09-01.
 */
public class ResizeViewHelper {
    public static int getLayoutHeight(View view) {

        final int width = view.getWidth();

        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        return view.getMeasuredHeight();
    }

    public static void animateChangeSizeOfView(final View v, final int from, final int to) {

        if(from == to){
            return;
        }

        final int initialHeight = v.getMeasuredHeight();
        if((to - from) < 0){
            v.getLayoutParams().height = from;
            v.requestLayout();
        }

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int h = 0;
                if((to - from) > 1){
                    h = interpolatedTime == 1? RecyclerView.LayoutParams.WRAP_CONTENT : from + (int) ((to - from) * interpolatedTime);
                    v.getLayoutParams().height = h;
                    v.requestLayout();
                }
                else{
                    h = from + (int) ((to - from) * interpolatedTime);
                    v.getLayoutParams().height = h;
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return false;
            }
        };

        a.setDuration((int) (Math.abs(to - from)*2 / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
