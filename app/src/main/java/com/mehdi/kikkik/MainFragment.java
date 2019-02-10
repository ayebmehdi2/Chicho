package com.mehdi.kikkik;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehdi.kikkik.databinding.LayoutRecycleBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment implements AdapterPost.Click {

    LayoutRecycleBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;
    ChildEventListener childEventListener;

    ArrayList<DATA_POST> list;
    private AdapterPost postAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();

        binding = DataBindingUtil.inflate(inflater,R.layout.layout_recycle, container, false);
        postAdapter = new AdapterPost(getContext(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(postAdapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("POSTS");

        /*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    DATA_POST post = data.getValue(DATA_POST.class);
                    data_posts.add(post);
                }
                postAdapter.swapAdapter(data_posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DATA_POST post = dataSnapshot.getValue(DATA_POST.class);
                list.add(post);
                Log.d("MainFragment : ", String.valueOf(list.size()));
                Log.d("MainFragment : ", "name " + " : " + post.getName());
                Log.d("MainFragment : ", "content " + " : " + post.getContent());
                postAdapter.swapAdapter(list);
                //binding.recycler.setAdapter(postAdapter);

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


        binding.sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = binding.contentShare.getText().toString();
                if (content != null && content.length() > 0){
                    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    final String uid = preferences.getString(getString(R.string.uid), "");
                    int numPost = preferences.getInt("numPost",1);
                    String chiledPost = uid + String.valueOf(numPost);
                    DATA_POST data_post = new DATA_POST(chiledPost, preferences.getString("img", null), preferences.getString("name", "Error"), (int) System.currentTimeMillis(), content
                            , 0, 0, false, false);
                    reference.child(chiledPost).setValue(data_post);
                    database.getReference().child("USERS").child(uid).child("numPost").setValue(numPost + 1);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (childEventListener != null){
            reference.removeEventListener(childEventListener);
            childEventListener = null;
            list.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reference.addChildEventListener(childEventListener);
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
