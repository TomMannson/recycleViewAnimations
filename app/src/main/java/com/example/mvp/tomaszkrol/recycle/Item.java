package com.example.mvp.tomaszkrol.recycle;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp.tomaszkrol.recycle.databinding.RecycleItemBinding;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by tomasz.krol on 2016-08-29.
 */
public class Item extends AbstractFlexibleItem<Item.Holder> {
    public static final int ADD = 0, REMOVE = 1, MODIFIE = 2;

    int id;
    int color;

    public Item(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item) {
            return ((Item) o).id == this.id;
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycle_item;
    }

    @Override
    public Holder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                   ViewGroup parent) {

        RecycleItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recycle_item,
                parent, false);

        return new Holder(binding, adapter);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter adapter, final Holder holder, int position,
                               List payloads) {
        holder.binding.text.setText(color + "");
//        holder.setColor(color);
//        holder.binding.getRoot().setBackgroundColor(color);
        int visibility = holder.binding.additionalData.getVisibility();
        if(payloads.size() > 0) {
            if("CHANGE".equals(payloads.get(0))
                    && holder.binding.additionalData.getVisibility() == View.GONE
                    && holder.binding.content.getVisibility() == View.GONE ) {
                holder.binding.additionalData.setVisibility(View.VISIBLE);
            }
            else if("CHANGE".equals(payloads.get(0))
                    && holder.binding.additionalData.getVisibility() == View.VISIBLE
                    && holder.binding.content.getVisibility() == View.GONE ) {
                holder.binding.additionalData.setVisibility(View.GONE);
                holder.binding.content.setVisibility(View.VISIBLE);
            }
            else if("CHANGE".equals(payloads.get(0))
                    && holder.binding.additionalData.getVisibility() == View.GONE
                    && holder.binding.content.getVisibility() == View.VISIBLE ) {
                holder.binding.content.setVisibility(View.GONE);
            }
        }
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(holder.isRecyclable()) {
                ((RAdapter) adapter).performAction(holder);
//                }
            }
        });
        holder.binding.executePendingBindings();
    }

    public static class Holder extends FlexibleViewHolder/* implements CustomAnimator.IColorChanging*/ {

        int color;
        boolean expand;
        RecycleItemBinding binding;

//        public Holder(RecycleItemBinding binding) {
////            super(binding.getRoot());
//            this.binding = binding;
//        }

        public Holder(RecycleItemBinding binding, FlexibleAdapter adapter) {
            super(binding.getRoot(), adapter);
            this.binding = binding;
//            super(inflate, adapter);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Holder{" +
                    "color=" + color +
                    '}';
        }

//        @Override
//        public View getColorChangeTarget() {
//            return binding.getRoot();
//        }
    }
}
