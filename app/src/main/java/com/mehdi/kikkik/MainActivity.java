package com.mehdi.kikkik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehdi.kikkik.Messaging.FragMessage;
import com.mehdi.kikkik.Messaging.RoomChatAcivity;
import com.mehdi.kikkik.Post.MainFragment;
import com.mehdi.kikkik.Post.PostActivity;
import com.mehdi.kikkik.Profile.UserProfile;
import com.mehdi.kikkik.Search.FragSearch;
import com.mehdi.kikkik.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements MainFragment.clickMainFragment, FragSearch.ClickFragSearch, FragMessage.clickItem{

    private ActivityMainBinding binding;
    private String name = "Not Defined";
    private String imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uid = preferences.getString(getString(R.string.uid), null);
        if (uid != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            DatabaseReference nameRefrence = reference.child("name");
            nameRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String na = dataSnapshot.getValue(String.class);
                    if (na != null){
                        name = na;
                        editor.putString("name", name);
                        editor.apply();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            DatabaseReference imgRefrence = reference.child("img");
            imgRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String img = dataSnapshot.getValue(String.class);
                    if (img != null){
                        imgUri = img;
                        Picasso.get().load(Uri.parse(imgUri)).into(binding.acc);
                        editor.putString("img", img);
                        editor.apply();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            final DatabaseReference numPostRef = reference.child("numPost");
            numPostRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Integer numPost = dataSnapshot.getValue(Integer.class);
                    editor.putInt("numPost", numPost);
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            editor.apply();
        }else {
            startActivity(new Intent(this, SignUpActivity.class));
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frag, new MainFragment()).commit();
        }

        if (imgUri != null) Picasso.get().load(Uri.parse(imgUri)).into(binding.acc);

        binding.acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });

        binding.serT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.serT.setVisibility(View.GONE);
                binding.serM.setVisibility(View.VISIBLE);
                defaul();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag, new FragSearch()).commit();
            }
        });

    }

    public void defSear(){
        binding.serT.setVisibility(View.VISIBLE);
        binding.serM.setVisibility(View.GONE);

    }

    public void main(View view){
        defaul();
        defSear();
        binding.main.setImageDrawable(getResources().getDrawable(R.drawable.ic_happy_2));
        binding.main.setBackgroundResource(R.drawable.show_2);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, mainFragment).commit();
    }

    public void messaging(View view){
        defaul();
        defSear();
        binding.msg.setImageDrawable(getResources().getDrawable(R.drawable.ic_messaging_2));
        binding.msg.setBackgroundResource(R.drawable.show_2);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, new FragMessage()).commit();
    }

    public void not(View view){
        defSear();
        defaul();
        binding.not.setImageDrawable(getResources().getDrawable(R.drawable.ic_bell_2));
        binding.not.setBackgroundResource(R.drawable.show_2);
        //getSupportFragmentManager().beginTransaction().replace(0, null).commit();
    }

    public void defaul(){
        binding.main.setImageDrawable(getResources().getDrawable(R.drawable.ic_happy));
        binding.main.setBackgroundResource(0);
        binding.msg.setImageDrawable(getResources().getDrawable(R.drawable.ic_messaging));
        binding.msg.setBackgroundResource(0);
        binding.not.setImageDrawable(getResources().getDrawable(R.drawable.ic_bell));
        binding.not.setBackgroundResource(0);
    }

    @Override
    public void postActivity() {
        startActivity(new Intent(this, PostActivity.class));
    }

    @Override
    public void clickpp(String chN) {
        Intent i = new Intent(this, RoomChatAcivity.class);
        i.putExtra("uid", chN);
        startActivity(i);
    }

    @Override
    public void msg(String uid) {
        Intent i = new Intent(MainActivity.this, RoomChatAcivity.class);
        i.putExtra("uid", uid);
        startActivity(i);
    }

}
