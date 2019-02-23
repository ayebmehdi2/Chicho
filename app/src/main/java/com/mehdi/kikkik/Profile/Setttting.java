package com.mehdi.kikkik.Profile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.mehdi.kikkik.R;
import com.mehdi.kikkik.SignUpActivity;
import com.mehdi.kikkik.databinding.SettingBinding;

public class Setttting extends AppCompatActivity {

    SettingBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.setting);
    }

    public void logout(View view){
        AuthUI.getInstance().signOut(this);
        startActivity(new Intent(this, SignUpActivity.class));
    }

}
