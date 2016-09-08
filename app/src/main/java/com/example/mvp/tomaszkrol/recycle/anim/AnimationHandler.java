package com.example.mvp.tomaszkrol.recycle.anim;

import android.view.View;

/**
 * Created by tomasz.krol on 2016-09-08.
 */
public interface AnimationHandler {

    boolean animShouldPerform();

    void performOnStart(View v);

    void performAnimationFrame(View v, float time);
}
