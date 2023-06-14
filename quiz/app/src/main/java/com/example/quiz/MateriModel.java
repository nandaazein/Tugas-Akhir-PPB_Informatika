package com.example.quiz;

public class MateriModel {

    String key, judul_materi, bagian_materi, isi_materi, image;

    public MateriModel() {
        // dibutuhkan oleh Firebase
    }

    public MateriModel(String key, String judul_materi, String bagian_materi, String isi_materi) {
        this.key = key;
        this.judul_materi = judul_materi;
        this.bagian_materi = bagian_materi;
        this.isi_materi = isi_materi;
    }

    public void setImage(String Image){
        this.image = Image;
    }
    public String getImage(){
        return image;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getkey() {
        return key;
    }

    public String getJudul_materi() {
        return judul_materi;
    }

    public String getBagian_materi() {
        return bagian_materi;
    }

    public String getIsi_materi() {
        return isi_materi;
    }
}
