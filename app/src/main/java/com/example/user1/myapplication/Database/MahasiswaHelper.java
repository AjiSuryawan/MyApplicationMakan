package com.example.user1.myapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.user1.myapplication.Model.ModelDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.NAMA;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.NIM;
import static com.example.user1.myapplication.Database.DatabaseContract.TABLE_NAME;

/**
 * Created by dicoding on 12/1/2016.
 */

public class MahasiswaHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MahasiswaHelper(Context context){
        this.context = context;
    }

    public MahasiswaHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    /**
     * Gunakan method ini untuk cari NIM berdasarkan nama mahasiswa
     * @param nama nama yang dicari
     * @return NIM dari mahasiswa
     */
    public ArrayList<ModelDatabase> getDataByName(String nama){
        String result = "";
        Cursor cursor = database.query(TABLE_NAME,null,NAMA+" LIKE ?",new String[]{nama+ "%"},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<ModelDatabase> arrayList = new ArrayList<>();
        ModelDatabase mahasiswaModel;
        if (cursor.getCount()>0) {
            do {
                mahasiswaModel = new ModelDatabase();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    /**
     * Gunakan method ini untuk mendapatkan semua data mahasiswa
     * @return hasil query mahasiswa model di dalam arraylist
     */
    public ArrayList<ModelDatabase> getAllData(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<ModelDatabase> arrayList = new ArrayList<>();
        ModelDatabase mahasiswaModel;
        if (cursor.getCount()>0) {
            do {
                mahasiswaModel = new ModelDatabase();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    /**
     * Gunakan method ini untuk memulai sesi query transaction
     */
    public void beginTransaction(){
        database.beginTransaction();
    }

    /**
     * Gunakan method ini jika query transaction berhasil, jika error jangan panggil method ini
     */
    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    /**
     * Gunakan method ini untuk mengakhiri sesi query transaction
     */
    public void endTransaction(){
        database.endTransaction();
    }

    /**
     * Gunakan method ini untuk query insert di dalam transaction
     * @param mahasiswaModel inputan model mahasiswa
     */
    public void insertTransaction(ModelDatabase mahasiswaModel){
        String sql = "INSERT INTO "+TABLE_NAME+" ("+NAMA+", "+NIM+") VALUES (?, ? )";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getName());
        stmt.bindString(2, mahasiswaModel.getNim());
        stmt.execute();
        stmt.clearBindings();
        Log.d("sukses", "insertTransaction: ");
    }
}
