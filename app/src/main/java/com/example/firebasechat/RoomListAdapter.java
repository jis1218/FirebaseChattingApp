package com.example.firebasechat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebasechat.model.Room;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-03.
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.MyHolder> {
    String friend_name;
    String last_msg;
    ArrayList<Room> list;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Room room = list.get(position);
        holder.room = room;
        holder.tvFName.setText(room.getFriendString());
        holder.tvLastMsg.setText(room.lastMsg);
        holder.tvLastMsgTime.setText(String.valueOf(room.lastMsgTime));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder{

        Room room;
        TextView tvFName, tvLastMsg, tvLastMsgTime;

        public MyHolder(final View itemView) {
            super(itemView);
            tvFName = (TextView) itemView.findViewById(R.id.tvFName);
            tvLastMsg = itemView.findViewById(R.id.tvLastMsg);
            tvLastMsgTime = itemView.findViewById(R.id.tvLastMsgTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),RoomActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
