package com.mehdi.kikkik.Messaging;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehdi.kikkik.R;

import java.util.ArrayList;

public class FragMessage extends Fragment implements AdapPersonMessaging.clickMessagingPerson {

    private AdapPersonMessaging perMes;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private ArrayList<UserInfo> messageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple_recycle, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("PERSON_MESSAGE");

        RecyclerView recyclerView = view.findViewById(R.id.simple_recycl);
        perMes = new AdapPersonMessaging(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(perMes);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (childEventListener != null){
            reference.removeEventListener(childEventListener);
            childEventListener = null;
            messageList.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        messageList = new ArrayList<>();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                if (info == null) return;
                messageList.add(new UserInfo(info.getChildName(), info.getYouName(), info.getYourPhoto(), info.getLastMessage()));
                perMes.swapAdapter(messageList);
            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                if (info == null) return;
                int index = messageList.indexOf(info);
                messageList.set(index, info);
                perMes.swapAdapter(messageList);

            }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);

    }

    public interface clickItem{
        void clickpp(String chN);
    }

    private clickItem click;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            click = (clickItem) context;
        }catch (ClassCastException e){

        }
    }

    @Override
    public void clickAllItem(String chiledName) {
        click.clickpp(chiledName);
    }
}
