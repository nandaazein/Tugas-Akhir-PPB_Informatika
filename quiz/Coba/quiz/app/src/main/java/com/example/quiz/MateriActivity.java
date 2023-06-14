package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.praktikum5.modelData.Artikel;
import com.example.quiz.Models.Artikel;

import java.util.ArrayList;

public class MateriActivity extends AppCompatActivity {


    int indexOfArtikel = 0;

    //        komponen
    TextView Judul;
    TextView konten;
    ImageView gambar;

    //        tombol navigasi
    Button prev;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

//        TextView tvNama = findViewById(R.id.nama);
//
//        ArrayList<String> namaMahasiswa = new ArrayList<String>();
//        namaMahasiswa.add("yogie");
//        namaMahasiswa.add("yulian");
//        namaMahasiswa.add("Fahmi");
//        namaMahasiswa.add("Dzaki");
//
//        String namaV = " ";
//        for(String nama : namaMahasiswa){
//            namaV += " | "+nama;
//        }
//        tvNama.setText(namaV);

        Artikel[] artikels = {
                new Artikel("A. Apa itu HTML?", R.string.bagajah),
                new Artikel("B. Dasar HTML",  R.string.baamar),
                new Artikel("C. HTML JavaScript", R.string.hwakun),

        };

        Judul = findViewById(R.id.judul);
        konten = findViewById(R.id.konten);
        gambar = findViewById(R.id.gambar);

        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);


        tampilkan(artikels[indexOfArtikel]);
        prev.setOnClickListener(v->{
            if(indexOfArtikel != 0){
                indexOfArtikel--;
                tampilkan(artikels[indexOfArtikel]);
            }else{
                Toast.makeText(this, "habis", Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(v->{
            if(indexOfArtikel != artikels.length-1){
                indexOfArtikel++;
                tampilkan(artikels[indexOfArtikel]);
            }else{
                Toast.makeText(this, "habis", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkan(Artikel artikel) {
        Judul.setText(artikel.getJudul());
        gambar.setImageResource(artikel.getGambar());
        konten.setText(getString(artikel.getKonten()));
    }
}