package com.example.user1.myapplication.Database;

import android.provider.BaseColumns;

/**
 * Created by dicoding on 10/18/2017.
 */

public class DatabaseContract {

    static String TABLE_NAME = "tb_makanan";
    static String TABLE_NAME2 = "tbuser";

    static final class MahasiswaColumns implements BaseColumns {

        static String NAMAGURU = "NAMA";

        static String PERTANYAAN = "pertanyaan";

        static String JAWAWAN = "jawaban";

        static String TIPE = "tipe";
    }
}
