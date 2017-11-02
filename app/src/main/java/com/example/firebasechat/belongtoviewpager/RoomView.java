package com.example.firebasechat.belongtoviewpager;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.firebasechat.R;

/**
 * Created by 정인섭 on 2017-11-02.
 */

public class RoomView extends FrameLayout {
    public RoomView(@NonNull Context context) {
        super(context);
        initView();
    }

    public RoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.room_layout, null);
        addView(view);
    }
}
