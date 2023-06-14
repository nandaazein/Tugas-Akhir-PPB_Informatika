package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MenuMateri extends AppCompatActivity {

    private DatabaseReference database;
    ListView listView;
    FloatingActionButton FAB;
    private ArrayList<MateriModel> listMateri;
    private ProgressDialog progressDialog;
    String imageName;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_materi);

        listView = findViewById(R.id.itemsList);
        FAB = findViewById(R.id.tambahData);

//        menginsiasi database Firebase
        database = FirebaseDatabase.getInstance().getReference();

        FAB.setOnClickListener(v->{
            Intent intent = new Intent(MenuMateri.this,inputForm.class);
            intent.putExtra("key","0");
            startActivity(intent);
        });
    }

    public void populateListview(){
        try {
            itemListAdapter itemsAdopter = new itemListAdapter(this, listMateri);
            listView.setAdapter(itemsAdopter);
            itemsAdopter.notifyDataSetChanged();
            registerForContextMenu(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateDataMateri(){
        database.child("Materi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMateri = new ArrayList<>();
                for (DataSnapshot materiSnapshot : snapshot.getChildren()) {
                    MateriModel materi = materiSnapshot.getValue(MateriModel.class);
                    materi.setKey(materiSnapshot.getKey());
                    listMateri.add(materi);
                }

                populateListview();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        MateriModel materi = listMateri.get(info.position);
        switch (item.getItemId()) {
            case R.id.detail:
                Intent intent1 = new Intent(MenuMateri.this, DetailMateriActivity.class);
                intent1.putExtra("key", materi.getkey());
                startActivity(intent1);
                return true;
            case R.id.edit:
                Intent intent = new Intent(MenuMateri.this, inputForm.class);
                intent.putExtra("key",materi.getkey());
                startActivity(intent);
                return true;
            case R.id.delete:
                showConfirmationDialogAndDeleteImage(materi.getkey(), materi.image);
                populateDataMateri();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void showConfirmationDialogAndDeleteImage(String key, String imageName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus data?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteImageFromStorage(imageName);
                        database.child("Materi").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MenuMateri.this, "Data dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tindakan yang dilakukan ketika user menekan tombol Tidak
                        // Contoh: tutup dialog
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteImageFromStorage(String imageName) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images").child(imageName);

        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Gambar berhasil dihapus dari storage
                Toast.makeText(MenuMateri.this, "Gambar dihapus dari storage", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Gagal menghapus gambar dari storage
                Toast.makeText(MenuMateri.this, "Gagal menghapus gambar dari storage", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        populateDataMateri();
    }

}