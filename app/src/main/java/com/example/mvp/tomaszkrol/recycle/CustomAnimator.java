package com.example.mvp.tomaszkrol.recycle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mvp.tomaszkrol.recycle.utils.ColorAnimator;
import com.example.mvp.tomaszkrol.recycle.utils.ResizeViewHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tomasz.krol on 2016-08-29.
 */
public class CustomAnimator extends DefaultItemAnimator {

    HashMap<RecyclerView.ViewHolder, AnimatorInfo> animatorMap = new HashMap<>();
    boolean moveLock = false;

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {

        CustomHolderInfo info = new CustomHolderInfo();
        info.setFrom(viewHolder);
        info.setColorTransformInfo(viewHolder);
        return info;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder) {

        CustomHolderInfo info = new CustomHolderInfo();
        info.setFrom(viewHolder);
        info.setColorTransformInfo(viewHolder);
        return info;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        if (moveLock) {
            dispatchMoveFinished(holder);
            return true;
        }

        return super.animateMove(holder, fromX, fromY, toX, toY);
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {

        moveLock = true;
        performAnimationChange(newHolder, oldHolder,
                (CustomHolderInfo) preInfo, (CustomHolderInfo) postInfo);

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }

    public void performAnimationChange(@NonNull final RecyclerView.ViewHolder holder, @NonNull final RecyclerView.ViewHolder oldholder,
                                       @NonNull final CustomHolderInfo preInfo,
                                       @NonNull CustomHolderInfo postInfo) {


        if (preInfo.color != postInfo.color) {

            IColorChanging colorHolder = (IColorChanging)holder;
            final ObjectAnimator crossFadeColor = ColorAnimator.asArgb(
                    colorHolder.getColorChangeTarget(),
                    "backgroundColor",
                    preInfo.color, postInfo.color);

            AnimatorSet bgAnim = new AnimatorSet();
            bgAnim.play(crossFadeColor);
            bgAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dispatchAnimationStarted(holder);
                    dispatchAnimationStarted(oldholder);
                    animatorMap.remove(holder);
                    moveLock = false;
                }
            });

            AnimatorInfo animatorInfo = animatorMap.get(holder);
//
            if (animatorInfo != null) {
                crossFadeColor.setCurrentPlayTime(animatorInfo.crosFadeAnim.getCurrentPlayTime());
                animatorInfo.bgAnim.cancel();
                animatorMap.remove(holder);
            }

            animatorMap.put(holder, new AnimatorInfo(bgAnim, crossFadeColor));
            bgAnim.start();
        } else {
            dispatchAnimationStarted(holder);
            dispatchAnimationStarted(oldholder);
        }

        ResizeViewHelper.animateChangeSizeOfView(holder.itemView,
                preInfo.height,
                postInfo.height,
                preInfo.width, postInfo.width);
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        return super.animatePersistence(viewHolder, preInfo, postInfo);
    }

    public static class CustomHolderInfo extends ItemHolderInfo {
        int color;
        int colorChangeTarget;

        int height;
        int width;

        @Override
        public ItemHolderInfo setFrom(RecyclerView.ViewHolder holder) {
                height = ResizeViewHelper.getLayoutHeight(holder.itemView);
                width = ResizeViewHelper.getLayoutWidth(holder.itemView);
            return super.setFrom(holder);
        }

        public ItemHolderInfo setColorTransformInfo(RecyclerView.ViewHolder holder) {
            if (holder instanceof IColorChanging) {
                IColorChanging colorViewHolder = (IColorChanging) holder;
                color = ((ColorDrawable) colorViewHolder.getColorChangeTarget().getBackground()).getColor();
            }
            return super.setFrom(holder);
        }

        @Override
        public String toString() {
            return "ItemHolderInfo{" +
                    "color=" + color +
                    ", height=" + height +
                    '}';
        }
    }

    private class AnimatorInfo {
        Animator bgAnim;
        ObjectAnimator crosFadeAnim;

        public AnimatorInfo(Animator bgAnim, ObjectAnimator crosFadeAnim) {
            this.bgAnim = bgAnim;
            this.crosFadeAnim = crosFadeAnim;
        }
    }

    public interface IColorChanging {
        View getColorChangeTarget();
    }
}
