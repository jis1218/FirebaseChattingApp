package com.example.firebasechat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 정인섭 on 2017-11-03.
 */

public class Room {
    public String id;
    public String title;
    public String lastMsg;
    public long lastMsgTime;
    public int msgCount;
    public long creation_time;
    public ArrayList<User> friend_list;

    public String getFriendString(){
        String friendString = "";
        if(friend_list != null && friend_list.size()>0){
            for(User friend : friend_list){
                friendString += ", " + friend.mName;
            }
        }

        return "";
    }
}
