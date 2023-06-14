package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class inputForm extends AppCompatActivity {

    TextView TVjudul_materi,TVbagian_materi,TVisi_materi;
    private DatabaseReference database;
    String judul_materi,bagian_materi,isi_materi;
    Button simpan;
    MateriModel materi;

    String imageName;
    TextView TvImage;
    ImageView imagePreview;
    Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

//        mengambil textView
        TVjudul_materi = findViewById(R.id.judul_materi);
        TVbagian_materi = findViewById(R.id.bagian_materi);
        TVisi_materi = findViewById(R.id.isi_materi);;
        TvImage = findViewById(R.id.imageText);
        Button image  = findViewById(R.id.imageButton);
        imagePreview = findViewById(R.id.imagePreview);

        image.setOnClickListener(v->{
            Intent intentMedia = new Intent();
            intentMedia.setType("image/*");
            intentMedia.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intentMedia,"Select Image"), PICK_IMAGE_REQUEST);

        });
//        menginsialisasi database
        database = FirebaseDatabase.getInstance().getReference();

//        tombol simpan.
        simpan = findViewById(R.id.buttonSimpan);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if(!key.equals("0")){

//            mengambil 1 data berdasarkan key
            database.child("Materi").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String judul_materi = snapshot.child("judul_materi").getValue(String.class);
                    String bagian_materi = snapshot.child("bagian_materi").getValue(String.class);
                    String isi_materi = snapshot.child("isi_materi").getValue(String.class);
                    imageName = snapshot.child("image").getValue(String.class);

                    getSupportActionBar().setTitle("Edit data " + judul_materi);
                    TVjudul_materi.setText(judul_materi);
                    TVbagian_materi.setText(bagian_materi);
                    TVisi_materi.setText(isi_materi);

                    if(imageName!=null){
                        TvImage.setText(imageName);
                        previewImageUriFromStorage(imageName);
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    finish();
                }
            });
            Toast.makeText(this, "key "+key, Toast.LENGTH_SHORT).show();



            simpan.setOnClickListener(v->{
                judul_materi = TVjudul_materi.getText().toString();
                bagian_materi = TVbagian_materi.getText().toString();
                isi_materi = TVisi_materi.getText().toString();
                String newImage = TvImage.getText().toString();
//                membuat hashMap updates yang menyimpan object string
                Map<String, Object> updates = new HashMap<>();
                updates.put("judul_materi", judul_materi);
                updates.put("bagian_materi",bagian_materi);
                updates.put("isi_materi", isi_materi);
                Toast.makeText(this, "file "+filePath, Toast.LENGTH_SHORT).show();
                if(filePath != null){
                    deleteFiles(imageName);
                    uploadImage(filePath);
                    updates.put("image",getFileName(filePath));
                }

//                mengubdate database berdasarkan key
                database.child("Materi").child(key).updateChildren(updates);
                finish();
            });

        }else{
            getSupportActionBar().setTitle("Tambah Data");



            simpan.setOnClickListener(v->{
                judul_materi = TVjudul_materi.getText().toString();
                bagian_materi = TVbagian_materi.getText().toString();
                isi_materi = TVisi_materi.getText().toString();
                MateriModel materi = new MateriModel("",judul_materi,bagian_materi,isi_materi);

                if(filePath != null){
                    materi.setImage(getFileName(filePath));
                    uploadImage(filePath);
                }
                submitMateri(materi);
                finish();
            });
        }
    }


    public void submitMateri(MateriModel materi){
        database.child("Materi").push().setValue(materi).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(inputForm.this, "Berhasil tambah data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            // Mendapatkan referensi Firebase Storage

            StorageReference storageRef = storage.getReference();

            //Membuat referensi ke lokasi penyimpanan yang diinginkan dalam Firebase Storage (misalnya, "images")
            StorageReference imagesRef = storageRef.child("images/" + getFileName(filePath));

            // Melakukan unggahan menggunakan putFile dan menambahkan OnSuccessListener dan OnFailureListener
            imagesRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Unggahan berhasil
                            // Lakukan tindakan yang sesuai, seperti mendapatkan URL unduhan gambar
                            // menggunakan taskSnapshot.getDownloadUrl()
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Unggahan gagal
                            // Tangani kegagalan dengan tindakan yang sesuai
                        }
                    });
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void deleteFiles(String fileName) {
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/"+fileName);
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            String fileName = getFileName(filePath);
            TvImage.setText(fileName);
            // Menampilkan gambar ke preview
            Picasso.get().load(filePath).into(imagePreview);
        }
    }
}