package com.example.mvp.tomaszkrol.recycle;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CompoundButton;

import com.example.mvp.tomaszkrol.recycle.databinding.ActivityMainBinding;
import com.example.mvp.tomaszkrol.recycle.utils.ColorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ActivityMainBinding binding;
    List<Item> list = new ArrayList<>();
    private RAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Random r = new Random();
        for(int i = 0; i < 500; i++){
            list.add(new Item(i, ColorHelper.getRandomColor()));
        }

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.binding.list.setItemAnimator(new CustomAnimator());
        this.binding.list.setLayoutManager(new LinearLayoutManager(this));

        binding.addRadio.setOnCheckedChangeListener(this);
        binding.removeRadio.setOnCheckedChangeListener(this);
        binding.modifyRadio.setOnCheckedChangeListener(this);

        adapter = new RAdapter(list);
//        adapter.setData(list);
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(!b){
            return;
        }
        switch (compoundButton.getId()){
            case R.id.add_radio:{
                adapter.setActionType(RAdapter.ADD);
                break;
            }
            case R.id.remove_radio:{
                adapter.setActionType(RAdapter.REMOVE);
                break;
            }
            case R.id.modify_radio:{
                adapter.setActionType(RAdapter.MODIFIE);
                break;
            }
        }
    }
}
