package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailMateriActivity extends AppCompatActivity {

    TextView TVjudul_materi,TVbagian_materi,TVisi_materi,TVImage;
    private DatabaseReference database;
    String imageName;
    ImageView imagePreview;
    Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

//        mengambil textView
        TVjudul_materi = findViewById(R.id.judul_materi);
        TVbagian_materi = findViewById(R.id.bagian_materi);
        TVisi_materi = findViewById(R.id.isi_materi);
        TVisi_materi.setMovementMethod(new ScrollingMovementMethod());
        TVImage = findViewById(R.id.imageText);
        imagePreview = findViewById(R.id.imagePreview);

//        menginsialisasi database
        database = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
//            mengambil 1 data berdasarkan key
        database.child("Materi").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String judul_materi = snapshot.child("judul_materi").getValue(String.class);
                String bagian_materi = snapshot.child("bagian_materi").getValue(String.class);
                String isi_materi = snapshot.child("isi_materi").getValue(String.class);
                imageName = snapshot.child("image").getValue(String.class);

                getSupportActionBar().setTitle("Detail data " + judul_materi);
                TVjudul_materi.setText("Judul Materi : " + judul_materi);
                TVbagian_materi.setText("Bagian Materi : " + bagian_materi);
                TVisi_materi.setText("Isi Materi : " + isi_materi);



                if(imageName!=null){
                    TVImage.setText("" + imageName);
                    previewImageUriFromStorage(imageName);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
        Toast.makeText(this, "key "+key, Toast.LENGTH_SHORT).show();
    }
    public void previewImageUriFromStorage(String fileName){
        StorageReference storageRef = storage.getReference();
        storageRef.child("images").child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imagePreview);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
            }
        });
    }
}