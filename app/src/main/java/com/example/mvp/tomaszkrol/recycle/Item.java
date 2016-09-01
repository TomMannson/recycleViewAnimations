package com.example.mvp.tomaszkrol.recycle;

/**
 * Created by tomasz.krol on 2016-08-29.
 */
public class Item {
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
}
