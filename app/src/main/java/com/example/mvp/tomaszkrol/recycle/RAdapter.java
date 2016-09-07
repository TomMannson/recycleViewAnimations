package com.example.mvp.tomaszkrol.recycle;

import android.support.annotation.NonNull;

import com.example.mvp.tomaszkrol.recycle.utils.ColorHelper;

import java.util.List;
import java.util.Random;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by tomasz.krol on 2016-08-29.
 */
public class RAdapter extends FlexibleAdapter<Item> {

    public static final int ADD = 0, REMOVE = 1, MODIFIE = 2;

    private int actionType;

    public RAdapter(@NonNull List<Item> items) {
        super(items);
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void performAction(Item.Holder holder) {
        int position = holder.getAdapterPosition();
        switch (actionType) {
            case RAdapter.ADD: {
                addItem(position,
                        new Item(new Random().nextInt(), ColorHelper.getRandomColor()));
                break;
            }
            case RAdapter.REMOVE: {
                this.removeItem(position);
                break;
            }
            case RAdapter.MODIFIE: {
                holder.expand = true;
                updateItem(position,
                        new Item(new Random().nextInt(), ColorHelper.getRandomColor())
                        , "CHANGE");
                break;
            }
        }
    }
}
