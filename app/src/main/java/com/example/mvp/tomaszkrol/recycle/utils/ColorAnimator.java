package com.example.mvp.tomaszkrol.recycle.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;

/**
 * Created by tomasz.krol on 2016-09-01.
 */
public class ColorAnimator {

    public static ObjectAnimator asArgb(Object target, String propertyName, Object... values) {
        return ObjectAnimator.ofObject(target,
                propertyName,
                new ArgbEvaluator(),
                values);
    }

}
