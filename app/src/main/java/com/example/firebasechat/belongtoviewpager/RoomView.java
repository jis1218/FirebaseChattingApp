package com.example.firebasechat.belongtoviewpager;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.firebasechat.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by 정인섭 on 2017-11-02.
 */

public class RoomView extends FrameLayout {
    RoomRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    public RoomView(@NonNull Context context, FirebaseAuth auth) {
        super(context);
        this.mAuth = auth;
        initView();
    }

    public RoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.room_layout, null);
        setRecyclerView(view);

        addView(view);
    }

    public void setRecyclerView(View view){
        recyclerView = view.findViewById(R.id.roomRecyclerView);
        adapter = new RoomRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
