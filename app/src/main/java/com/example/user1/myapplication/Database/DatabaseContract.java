package com.example.user1.myapplication.Database;

import android.provider.BaseColumns;

/**
 * Created by dicoding on 10/18/2017.
 */

public class DatabaseContract {

    static String TABLE_NAME = "tb_makanan";

    static final class MahasiswaColumns implements BaseColumns {

        // Mahasiswa nama
        static String NAMA = "nama";
        // Mahasiswa nim
        static String NIM = "nim";

        static String URI = "uri";
    }
}
