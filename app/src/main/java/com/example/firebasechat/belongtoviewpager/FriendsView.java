package com.example.firebasechat.belongtoviewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.firebasechat.R;
import com.example.firebasechat.model.Friends;
import com.example.firebasechat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-02.
 */

public class FriendsView extends FrameLayout {
    FriendsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    FirebaseUser fUser;



    public FriendsView(Context context, FirebaseAuth mAuth) {
        super(context);
        this.mAuth = mAuth;
        initView();
    }

    public FriendsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.friend_layout, null);
        setRecyclerView(view);
        addView(view);
        setDatabase();
        getDatabase();

    }

    public void setRecyclerView(View view){
        recyclerView = view.findViewById(R.id.friendRecyclerView);
        adapter = new FriendsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setDatabase(){
        database = FirebaseDatabase.getInstance();
        fUser = mAuth.getCurrentUser();
        userRef = database.getReference("user");

    }

    public void getDatabase(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Friends> list = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Friends friends = snapshot.child(fUser.getUid()).child("friends").getValue(Friends.class);
                    list.add(friends);
                }
                adapter.refreshData(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
