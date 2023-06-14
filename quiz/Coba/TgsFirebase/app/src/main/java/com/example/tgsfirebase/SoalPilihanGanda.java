package com.example.tgsfirebase;

public class SoalPilihanGanda {

    //membuat array untuk pertanyaan
    public String pertanyaan[] = {
            "Apa itu bahasa pemrograman Java?",
            "Apa keuntungan menggunakan bahasa pemrograman Java",
            "Apa yang dimaksud dengan Java Virtual Machine (JVM)",
            "Apa yang dimaksud dengan write once, run anywhere dalam bahasa pemrograman Java",
            "Apa yang dimaksud dengan Objek-Oriented Programming (OOP) dalam bahasa pemrograman Java",

    };

    //membuat array untuk pilihan jawaban
    private String pilihanJawaban[][] = {
            {"Bahasa pemrograman yang hanya dapat dijalankan pada platform tertentu.",
                    "Bahasa pemrograman berorientasi objek yang dapat dijalankan pada berbagai platform.",
                    "Bahasa pemrograman yang hanya dapat dijalankan pada platform Windows.",
                    "Bahasa pemrograman yang hanya dapat dijalankan pada platform Linux."},
            {"Portabilitas, objek-oriented programming, memori management, multithreading, dan networking. ",
            "Portabilitas, objek-oriented programming, dan memori management.",
            "Multithreading dan networking. ",
            "Hanya dapat dijalankan pada platform tertentu."},
            {"Sebuah compiler untuk bahasa pemrograman Java. ",
                    "Sebuah bahasa pemrograman yang dapat dijalankan pada JVM.",
            "Sebuah lingkungan virtual yang dapat menjalankan kode Java pada berbagai platform",
            "Sebuah framework untuk membuat aplikasi Java."},
            {"Kode Java hanya bisa dijalankan pada platform tertentu saja.",
                    "Kode Java bisa dijalankan pada berbagai platform yang mendukung JVM",
                    "Kode Java hanya bisa dijalankan pada platform Windows",
                    "Kode Java tidak bisa dijalankan pada platform macOS",
            },
            {"Bahasa pemrograman yang hanya fokus pada perintah dan algoritma",
            "Bahasa pemrograman yang hanya fokus pada variabel dan fungsi",
            "Bahasa pemrograman yang memungkinkan pengembang untuk memodelkan objek-objek dalam dunia nyata ke dalam program ",
            "Bahasa pemrograman yang hanya digunakan untuk membuat aplikasi desktop",},

    };

    //membuat array untuk jawaban benar
    private String jawabanBenar[] = {
            "Bahasa pemrograman berorientasi objek yang dapat dijalankan pada berbagai platform.",
            "Portabilitas, objek-oriented programming, memori management, multithreading, dan networking. ",
            "Sebuah lingkungan virtual yang dapat menjalankan kode Java pada berbagai platform",
            "Kode Java bisa dijalankan pada berbagai platform yang mendukung JVM",
            "Bahasa pemrograman yang memungkinkan pengembang untuk memodelkan objek-objek dalam dunia nyata ke dalam program ",
            "Bambu runcing",
            "Monas",
            "Proklamasi",
            "Kalimantan",
    };

    //membuat getter untuk mengambil pertanyaan
    public String getPertanyaan(int x){
        String soal = pertanyaan[x];
        return soal;
    }

    //membuat getter untuk mengambil pilihan jawaban 1
    public String getPilihanJawaban1(int x){
        String jawaban1 = pilihanJawaban[x][0];
        return jawaban1;
    }

    //membuat getter untuk mengambil pilihan jawaban 2
    public String getPilihanJawaban2(int x){
        String jawaban2 = pilihanJawaban[x][1];
        return jawaban2;
    }

    //membuat getter untuk mengambil pilihan jawaban 3
    public String getPilihanJawaban3(int x){
        String jawaban3 = pilihanJawaban[x][2];
        return jawaban3;
    }

    //membuat getter untuk mengambil jawaban benar
    public String getJawabanBenar(int x){
        String jawaban = jawabanBenar[x];
        return jawaban;
    }
}
