package com.mehdi.kikkik.Post;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehdi.kikkik.R;
import com.mehdi.kikkik.databinding.LayoutRecycleBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment implements AdapterPost.Click {

    LayoutRecycleBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;
    ChildEventListener childEventListener;

    ArrayList<DATA_POST> list;
    private AdapterPost postAdapter;

    public interface clickMainFragment{
        void postActivity();
    }

    private clickMainFragment clickMain;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            clickMain = (clickMainFragment) context;
        }catch (ClassCastException ignore){ }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_recycle, container, false);
        postAdapter = new AdapterPost(getContext(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(postAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("POSTS");
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMain.postActivity();
            }
        });
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DATA_POST post = dataSnapshot.getValue(DATA_POST.class);
                if (post == null) return;
                if (post.getContent() == null && post.getContentImage() == null) return;
                list.add(post);
                postAdapter.swapAdapter(list);
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
        return binding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (childEventListener != null){
            reference.removeEventListener(childEventListener);
            childEventListener = null;
            list.clear();
        }
    }
    @Override
    public void like(String uid) {

    }
    @Override
    public void comment(String uid) {

    }
    @Override
    public void user(String uid) {

    }

}
