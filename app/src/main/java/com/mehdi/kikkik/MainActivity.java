package com.mehdi.kikkik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehdi.kikkik.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private String name = "Not Defined";
    private String imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (name != null) binding.name.setText(name);
        if (imgUri != null) Picasso.get().load(Uri.parse(imgUri)).into(binding.acc);

        getSupportFragmentManager().beginTransaction().add(R.id.frag, new MainFragment()).commit();

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
                        binding.name.setText(name);
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
    }

    public void main(View view){
        defaul();
        binding.main.setImageDrawable(getResources().getDrawable(R.drawable.ic_happy));
        binding.main.setBackgroundResource(R.drawable.show);
        MainFragment mainFragment = new MainFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frag, mainFragment).commit();
    }

    public void search(View view){
        defaul();
        binding.sear.setImageDrawable(getResources().getDrawable(R.drawable.top));
        binding.sear.setBackgroundResource(R.drawable.show);
        //getSupportFragmentManager().beginTransaction().replace(0, null).commit();


    }

    public void not(View view){
        defaul();
        binding.not.setImageDrawable(getResources().getDrawable(R.drawable.ic_bell));
        binding.not.setBackgroundResource(R.drawable.show);
        //getSupportFragmentManager().beginTransaction().replace(0, null).commit();
    }

    public void defaul(){
        binding.main.setImageDrawable(getResources().getDrawable(R.drawable.ic_happy_2));
        binding.main.setBackgroundResource(0);
        binding.sear.setImageDrawable(getResources().getDrawable(R.drawable.top_2));
        binding.sear.setBackgroundResource(0);
        binding.not.setImageDrawable(getResources().getDrawable(R.drawable.ic_bell_2));
        binding.not.setBackgroundResource(0);
    }
}
