package com.mehdi.kikkik.Messaging;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehdi.kikkik.Post.DATA_POST;
import com.mehdi.kikkik.R;
import com.mehdi.kikkik.databinding.RoomMessagingBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomChatAcivity extends AppCompatActivity {

    RoomMessagingBinding binding;

    private DatabaseReference referenceGlobal;
    ValueEventListener listenerForUser;

    private DatabaseReference referenceMsg;
    private ChildEventListener listenerForMesg;

    private String youName;
    private String yourImage;
    private String lastMesg;

    private AdapMessage adapMessage;
    private ArrayList<Message> messages;
    private int numComment = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.room_messaging);

        binding.yourName.setText(youName);
        Picasso.get().load(yourImage).into(binding.yourPh);

        adapMessage = new AdapMessage();
        binding.roomRecycle.setHasFixedSize(true);
        binding.roomRecycle.setLayoutManager(new LinearLayoutManager(this));
        binding.roomRecycle.setAdapter(adapMessage);

        final String uid = getIntent().getStringExtra("uid");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String typeChat = getIntent().getStringExtra("type");
        if (typeChat.equals("comment")){
            referenceMsg = database.getReference().child("POSTS").child(uid).child("COMMENT");
            referenceGlobal = database.getReference().child("POSTS").child(uid);
            listenerForUser = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DATA_POST userInfo = dataSnapshot.getValue(DATA_POST.class);
                    if (userInfo == null) return;
                    youName = userInfo.getName();
                    binding.yourName.setText(youName);
                    yourImage = userInfo.getImg();
                    Picasso.get().load(yourImage).into(binding.yourPh);
                    numComment = userInfo.getNumComment();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }else {
            referenceMsg = database.getReference().child("MESSAGS").child(uid);
            referenceGlobal = database.getReference().child("PERSON_MESSAGE").child(uid);
            listenerForUser = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo == null) return;
                    youName = userInfo.getYourPhoto();
                    binding.yourName.setText(youName);
                    yourImage = userInfo.getYouName();
                    Picasso.get().load(yourImage).into(binding.yourPh);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }

        listenerForUser = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if (userInfo == null) return;
                youName = userInfo.getYourPhoto();
                binding.yourName.setText(youName);
                yourImage = userInfo.getYouName();
                Picasso.get().load(yourImage).into(binding.yourPh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        messages = new ArrayList<>();
        listenerForMesg = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             Message message = dataSnapshot.getValue(Message.class);
             if (message == null) return;
             messages.add(message);
             adapMessage.swapAdapter(messages);
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

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.contentEdit.getText().toString();
                if (content.length() > 0) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RoomChatAcivity.this);
                    Message message = new Message(preferences.getString("name", ""), preferences.getString("img", ""), content,null, (int) System.currentTimeMillis());
                    referenceMsg.push().setValue(message);
                    lastMesg = content;
                    if (typeChat.equals("msg")){
                        referenceGlobal.child("lastMessage").setValue(lastMesg);
                    }else {
                        referenceGlobal.child("isComment").setValue(true);
                        referenceGlobal.child("numComment").setValue(numComment++);
                    }
                }
                    binding.contentEdit.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (referenceGlobal != null && listenerForUser != null) referenceGlobal.addValueEventListener(listenerForUser);
        if (referenceMsg != null && listenerForMesg != null) referenceMsg.addChildEventListener(listenerForMesg);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (listenerForMesg != null){
            referenceMsg.removeEventListener(listenerForMesg);
            listenerForMesg = null;
            messages.clear();
        }
        if (listenerForUser != null){
            referenceGlobal.removeEventListener(listenerForUser);
            listenerForUser = null;
        }
    }


}
