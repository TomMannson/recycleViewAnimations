package com.example.mvp.tomaszkrol.recycle.anim;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.List;

/**
 * Created by tomasz.krol on 2016-09-08.
 */
public class AnimPerformer {

    public static void performStart(final List<AnimationHandler> handlers, final View v,
                                    final OnAnimationEndListener listener){



        if(handlers == null){
            return;
        }

        for(int i = 0; i < handlers.size(); i++){
            AnimationHandler handler = handlers.get(i);
            if(handler.animShouldPerform()){
                handler.performOnStart(v);
            }
        }
        v.requestLayout();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                for(int i = 0; i < handlers.size(); i++){
                    AnimationHandler handler = handlers.get(i);
                    if(handler.animShouldPerform()){
                        handler.performAnimationFrame(v, interpolatedTime);
                    }
                }

                if(interpolatedTime == 1) {
                    listener.onAnimationEnd();
                }

                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return false;
            }
        };

//        a.setDuration((int) (Math.abs(hTo - hFrom)*2 / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(500);
        a.setInterpolator(v.getContext(), android.R.interpolator.accelerate_decelerate);
        new AccelerateDecelerateInterpolator();

        v.startAnimation(a);

    }

    public interface OnAnimationEndListener {

        void onAnimationEnd();

    }
}
