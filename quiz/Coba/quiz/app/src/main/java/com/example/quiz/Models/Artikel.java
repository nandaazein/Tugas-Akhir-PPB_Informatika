package com.example.quiz.Models;

public class Artikel {
    String Judul;
    int Gambar;
    int konten;

    public Artikel(String judul, int konten) {
        Judul = judul;

        this.konten = konten;
    }

    public String getJudul() {
        return Judul;
    }

    public int getGambar() {
        return Gambar;
    }

    public int getKonten() {
        return konten;
    }
}
