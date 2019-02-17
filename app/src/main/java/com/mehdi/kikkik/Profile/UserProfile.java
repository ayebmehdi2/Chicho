package com.mehdi.kikkik.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mehdi.kikkik.R;
import com.mehdi.kikkik.Setttting;
import com.mehdi.kikkik.databinding.ProfilAndroidBinding;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    ProfilAndroidBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.profil_android);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserProfile.this);
        binding.n.setText(preferences.getString("name", "none"));
        Picasso.get().load(preferences.getString("img", "")).into(binding.i);

        if (savedInstanceState != null) return;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_profil, new MyPost()).commit();

        binding.settingVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, Setttting.class));
            }
        });

    }

    public void maine(View view){
        defaul();
        binding.maine.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
        binding.maine.setBackgroundResource(R.drawable.show);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profil, new MyPost()).commit();
    }

    public void ami(View view){
        defaul();
        binding.ami.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_friend));
        binding.ami.setBackgroundResource(R.drawable.show);
    }

    public void cam(View view){
        defaul();
        binding.cam.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera));
        binding.cam.setBackgroundResource(R.drawable.show);
    }


    public void defaul(){
        binding.maine.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_2));
        binding.maine.setBackgroundResource(0);
        binding.ami.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_friend_2));
        binding.ami.setBackgroundResource(0);
        binding.cam.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera_2));
        binding.cam.setBackgroundResource(0);
    }

}
