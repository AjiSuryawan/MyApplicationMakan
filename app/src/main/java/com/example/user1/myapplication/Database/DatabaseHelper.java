package com.example.user1.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.NAMAGURU;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.PERTANYAAN;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.JAWAWAN;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.TIPE;
import static com.example.user1.myapplication.Database.DatabaseContract.TABLE_NAME;
import static com.example.user1.myapplication.Database.DatabaseContract.TABLE_NAME2;

/**
 * Created by dicoding on 12/1/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmahasiswa";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_MAHASISWA = "create table "+TABLE_NAME+
            " ("+_ID+" integer primary key autoincrement, " +
            PERTANYAAN +" text not null, " +
            JAWAWAN +" text not null," +
            TIPE +" text not null," +
            NAMAGURU +" text not null);";

    public static String CREATE_TABLE_MAHASISWA2 = "create table "+TABLE_NAME2+
            " ("+_ID+" integer primary key autoincrement, " + NAMAGURU +" text not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAHASISWA);
        db.execSQL(CREATE_TABLE_MAHASISWA2);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }
}
