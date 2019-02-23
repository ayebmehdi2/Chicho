package com.mehdi.kikkik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehdi.kikkik.Profile.DARNA;

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("USERS");

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    setupUser(user);
                }else {
                    signIn();
                }
            }
        };

    }

    public void signIn(){
        Log.d("SignUpActivity", "user null");
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                100);
    }

    private ValueEventListener responseListener;

    public void setupUser(final FirebaseUser user){

        final String uid = user.getUid();

        final SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this).edit();
        preferences.putString(getString(R.string.uid), uid);
        preferences.apply();

        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
        boolean hasProfile = preferencesGet.getBoolean("hasProfile", false);

        if (hasProfile){
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }else{
            responseListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        String name = user.getDisplayName();
                        Uri img = user.getPhotoUrl();
                        reference.child(uid).setValue(new DARNA(uid, name, img.toString(), 0));
                        preferences.putBoolean("hasProfile", true);
                    }

                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            reference.child(user.getUid()).addValueEventListener(responseListener);
            Log.d("SignUpActivity", "add valueEventListener");

        }

    }

    public void out(View view){
        AuthUI.getInstance().signOut(this);
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this).edit();
        preferences.putString(getString(R.string.uid), null);
        preferences.apply();
        Toast.makeText(this, "Sign Filed", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Sign Filed", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authStateListener);
        Log.d("SignUpActivity", "add authStateListener");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null){
            Log.d("SignUpActivity", "Remove authStateListener");
            auth.removeAuthStateListener(authStateListener);
        }
        if (responseListener != null){
            reference.removeEventListener(responseListener);
            Log.d("SignUpActivity", "Remove valueEventListener");
        }

    }
}
