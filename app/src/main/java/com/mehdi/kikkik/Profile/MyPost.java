package com.mehdi.kikkik.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.mehdi.kikkik.DATA_POST;
import com.mehdi.kikkik.R;

import java.util.ArrayList;

public class MyPost extends Fragment {

    public MyPost(){}

    private AdapMyPost posts;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private ArrayList<String> postsList;
    private String uid;

    public interface MyPostClick{
        void setClick();
    }

    private MyPostClick click;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_post_recycle, container, false);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("POSTS");

        RecyclerView recyclerView = view.findViewById(R.id.my_rec);

        posts = new AdapMyPost();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(posts);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        uid = preferences.getString(getString(R.string.uid), "null");
        Log.e("MyPost", "User ID : " + uid);

        postsList = new ArrayList<>();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DATA_POST post = dataSnapshot.getValue(DATA_POST.class);
                if (uid == null || uid.equals("null")) return;
                if (post == null) return;
                if (uid.equals(post.getUid())){
                    Log.e("MyPost", "uid post changed : " + post.getUid());
                    String conT = "%%%%%";
                    String conM = "%%%%%";
                    if (post.getContent() != null) conT = post.getContent();
                    if (post.getContentImage() != null) conM = post.getContentImage();
                    if (post.getContent() == null && post.getContentImage() == null) return;
                    postsList.add(conT + "," + conM);
                    posts.swapAdapter(postsList);
                }
                Log.e("MyPost", "Count my post list : " + postsList.size());
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

        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (childEventListener != null){
            reference.removeEventListener(childEventListener);
            childEventListener = null;
            postsList.clear();
        }
    }

}
