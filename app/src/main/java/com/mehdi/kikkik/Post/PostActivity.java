package com.mehdi.kikkik.Post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mehdi.kikkik.MainActivity;
import com.mehdi.kikkik.R;
import com.mehdi.kikkik.databinding.ActivityPostBinding;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {

    ActivityPostBinding binding;
    private Uri imageUri = null;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String uid;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        preferences = PreferenceManager.getDefaultSharedPreferences(PostActivity.this);
        uid = preferences.getString(getString(R.string.uid), "");
        binding.username.setText(preferences.getString("name", "none"));
        Picasso.get().load(preferences.getString("img", "")).into(binding.userlogo);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = database.getReference().child("POSTS");
        storageReference = storage.getReference().child("POSTI_MAGE");
        binding.photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 101);
            }
        });
        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new UploadTask().execute(imageUri);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            imageUri = data.getData();
            binding.contentImage.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUri).into(binding.contentImage);
        }
    }

    class UploadTask extends AsyncTask<Uri, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.layout.setVisibility(View.GONE);
            binding.pr.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Uri... voids) {
            uploadFile(voids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void vid) {
            super.onPostExecute(vid);
            startActivity(new Intent(PostActivity.this, MainActivity.class));
        }
    }

    public void uploadFile(Uri selectedImageUri){
        if (selectedImageUri != null){
            final StorageReference photoRef = storageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).continueWithTask(new Continuation<com.google.firebase.storage.UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<com.google.firebase.storage.UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return photoRef.getDownloadUrl(); }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        uploadData(task.getResult());
                    }
                } });
        }else {
            uploadData(null);
        }
    }

    public void uploadData(Uri uri){
        int numPost = preferences.getInt("numPost",1);
        String chiledPost = uid;
        String contentText = null;
        String contentImage = null;
        if (binding.contentText.getText().toString().length() > 0) contentText = binding.contentText.getText().toString();
        if (uri != null)  contentImage = uri.toString();
        if (contentText == null && contentImage == null) return;

        DATA_POST data_post = new DATA_POST(uid,
                                                preferences.getString("img", null),
                                                preferences.getString("name", "Error"),
                                                (int) System.currentTimeMillis(),
                                                contentText,
                                                contentImage,
                                                0, 0, false, false);

        reference.child(chiledPost).setValue(data_post);
        database.getReference().child("USERS").child(uid).child("numPost").setValue(numPost + 1);
        imageUri = null;
    }

}
