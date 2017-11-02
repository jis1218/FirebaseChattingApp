package com.example.firebasechat.belongtoviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 정인섭 on 2017-11-02.
 */

public class ViewPagerAdapter extends PagerAdapter {
    FriendsView friendsView;
    RoomView roomView;

    public ViewPagerAdapter(Context context) {
        friendsView = new FriendsView(context);
        roomView = new RoomView(context);
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