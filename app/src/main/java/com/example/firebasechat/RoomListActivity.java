package com.example.firebasechat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.firebasechat.belongtoviewpager.ViewPagerAdapter;
import com.example.firebasechat.model.Room;
import com.example.firebasechat.util.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity implements ISend{

    FrameLayout framePopup;
    Toolbar toolbar;
    FloatingActionButton fab;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    EditText editRoomName;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference roomRef;
    RoomListAdapter roomAdapter;
    RecyclerView roomRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        initField();
        initView();
        setListner();
        setAdapterToPager();
        setSupportActionBar(toolbar);
    }

    private void initField(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        roomRef = database.getReference("chatting_room");

    }

    private void initView(){
        framePopup = (FrameLayout) findViewById(R.id.framePopup);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        editRoomName = (EditText) findViewById(R.id.editRoomName);
        roomRecyclerView = (RecyclerView) viewPager.findViewById(R.id.roomRecyclerView);
    }

    private void setRoomRecyclerView(){
        roomAdapter = new RoomListAdapter();
        roomRecyclerView.setAdapter(roomAdapter);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showRoomList(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Room> list = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room room = null;
                    //if(snapshot.)
                    //room = snapshot.getValue()
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setListner(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(framePopup.getVisibility()==View.GONE)
                framePopup.setVisibility(View.VISIBLE);
                else if(framePopup.getVisibility()==View.VISIBLE){
                    String title = editRoomName.getText().toString();
                    String roomKey = roomRef.push().getKey();
                    Room room = new Room();
                    room.id = roomKey;
                    room.title = title;

                    roomRef.child(roomKey).setValue(room);

                    String user_id = PreferenceUtil.getStringValue(RoomListActivity.this, "user_id");
                    userRef.child(user_id).child("chatting_room").child(roomKey).setValue(room);
                    framePopup.setVisibility(View.GONE);


                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        framePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });


    }

    private void setAdapterToPager(){
        adapter = new ViewPagerAdapter(this, this);
        viewPager.setAdapter(adapter);
        setRoomRecyclerView();
    }

}
