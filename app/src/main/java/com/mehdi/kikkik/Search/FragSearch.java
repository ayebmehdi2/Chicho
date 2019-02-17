package com.mehdi.kikkik.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehdi.kikkik.DARNA;
import com.mehdi.kikkik.R;

import java.util.ArrayList;

public class FragSearch extends Fragment implements AdapPerson.clickPrson {

    AdapPerson person;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private ArrayList<DataPerson> personList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_recycle, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("USERS");

        RecyclerView recyclerView = view.findViewById(R.id.simple_recycle);
        person = new AdapPerson(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(person);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (childEventListener != null){
            reference.removeEventListener(childEventListener);
            childEventListener = null;
            personList.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        personList = new ArrayList<>();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DARNA user = dataSnapshot.getValue(DARNA.class);
                if (user == null) return;
                personList.add(new DataPerson(user.getUid(), user.getName(), user.getImg()));
                person.swapAdapter(personList);
                Log.e("MyPost", "Count my post list : " + personList.size());
            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    @Override
    public void follow(String uid) {

    }

    @Override
    public void msg(String uid) {

    }
}
