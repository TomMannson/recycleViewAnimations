package com.example.mvp.tomaszkrol.recycle;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp.tomaszkrol.recycle.databinding.RecycleItemBinding;
import com.example.mvp.tomaszkrol.recycle.utils.ColorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tomasz.krol on 2016-08-29.
 */
public class RAdapter extends RecyclerView.Adapter<RAdapter.Holder> {

    public static final int ADD = 0, REMOVE = 1, MODIFIE = 2;

    List<Item> data = new ArrayList<>();
    int operation = 0;
    private int actionType;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecycleItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recycle_item,
                parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.binding.text.setText(data.get(position).getColor() + "");
        holder.setColor(data.get(position).getColor());
        holder.binding.getRoot().setBackgroundColor(data.get(position).getColor());
        if (holder.expand) {
            int visibility = holder.binding.additionalData.getVisibility();
            holder.binding.additionalData.setVisibility(
                    visibility == View.GONE ? View.VISIBLE : View.GONE);

            holder.expand = false;
        }


        holder.setHeight(holder.binding.getRoot().getMeasuredHeight());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(holder.isRecyclable()) {
                performAction(holder);
//                }
            }
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    private void performAction(Holder holder) {
        int position = holder.getAdapterPosition();
        switch (actionType) {
            case RAdapter.ADD: {
                data.add(new Item(new Random().nextInt(), ColorHelper.getRandomColor()));
                notifyItemInserted(position);
                break;
            }
            case RAdapter.REMOVE: {
                if (position != -1) {
                    data.remove(position);
                    notifyItemRemoved(position);
                }
                break;
            }
            case RAdapter.MODIFIE: {
                holder.expand = true;
                data.set(position, new Item(new Random().nextInt(), ColorHelper.getRandomColor()));
                notifyItemChanged(position, "CHANGE");
                break;
            }
        }
    }

    public static class Holder extends RecyclerView.ViewHolder implements CustomAnimator.IColorChanging {

        int color;
        int height;
        boolean expand;
        RecycleItemBinding binding;

        public Holder(RecycleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "Holder{" +
                    "color=" + color +
                    ", height=" + height +
                    '}';
        }

        @Override
        public View getColorChangeTarget() {
            return binding.getRoot();
        }
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
