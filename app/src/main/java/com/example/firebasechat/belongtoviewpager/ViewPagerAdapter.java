package com.example.firebasechat.belongtoviewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasechat.model.Friends;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-02.
 */

public class ViewPagerAdapter extends PagerAdapter {
    FriendsView friendsView;
    RoomView roomView;
    RecyclerView recyclerView;
    ArrayList<Friends> list = new ArrayList<>();

    public ViewPagerAdapter(Context context, FirebaseAuth auth) {
        friendsView = new FriendsView(context, auth);
        roomView = new RoomView(context, auth);
    }

    private static final int COUNT = 2;


    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch(position){
            case 0 :
            default :
                container.addView(friendsView);

                return friendsView;
            case 1 :
                container.addView(roomView);
                return roomView;
        }
    }
}
