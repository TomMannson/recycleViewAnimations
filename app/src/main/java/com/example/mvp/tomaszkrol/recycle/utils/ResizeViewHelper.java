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

        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        return view.getMeasuredHeight();
    }

    public static int getLayoutWidth(View view) {

        final int width = view.getWidth();

        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        return view.getMeasuredWidth();
    }

    public static void animateChangeSizeOfView(final View v, final int hFrom, final int hTo,
            final int wFrom, final int wTo) {

        if(hFrom != hTo){
            v.getLayoutParams().height = hFrom;
            v.requestLayout();
        }

        

        v.getLayoutParams().width = wFrom;
        v.requestLayout();

        if(hFrom == hTo && wFrom == wTo){
            return
        }
        

        final int initialHeight = v.getMeasuredHeight();
        if((hTo - hFrom) < 0){
            v.getLayoutParams().height = hFrom;
            v.requestLayout();
        }

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int h = 0;
                if((hTo - hFrom) > 1){
                    h = interpolatedTime == 1? RecyclerView.LayoutParams.WRAP_CONTENT : hFrom + (int) ((hTo - hFrom) * interpolatedTime);
                    v.getLayoutParams().height = h;
                    v.requestLayout();
                }
                else{
                    h = hFrom + (int) ((hTo - hFrom) * interpolatedTime);
                    v.getLayoutParams().height = h;
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return false;
            }
        };

        a.setDuration((int) (Math.abs(hTo - hFrom)*2 / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
