package com.example.user1.myapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.NAMAGURU;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.PERTANYAAN;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.JAWAWAN;
import static com.example.user1.myapplication.Database.DatabaseContract.MahasiswaColumns.TIPE;
import static com.example.user1.myapplication.Database.DatabaseContract.TABLE_NAME;
import static com.example.user1.myapplication.Database.DatabaseContract.TABLE_NAME2;

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
    public ArrayList<Survey> getDataByName(String nama){
        String result = "";
        Cursor cursor = database.query(TABLE_NAME,null, NAMAGURU +" LIKE ?",new String[]{nama+ "%"},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<Survey> arrayList = new ArrayList<>();
        Survey mahasiswaModel;
        if (cursor.getCount()>0) {
            do {
                mahasiswaModel = new Survey();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setAnswer(cursor.getString(cursor.getColumnIndexOrThrow(PERTANYAAN)));
                mahasiswaModel.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(JAWAWAN)));
                mahasiswaModel.setType(cursor.getString(cursor.getColumnIndexOrThrow(TIPE)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<Survey> getAllData(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<Survey> arrayList = new ArrayList<>();
        Survey mahasiswaModel;
        if (cursor.getCount()>0) {
            do {
                mahasiswaModel = new Survey();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(PERTANYAAN)));
                mahasiswaModel.setAnswer(cursor.getString(cursor.getColumnIndexOrThrow(JAWAWAN)));
                mahasiswaModel.setType(cursor.getString(cursor.getColumnIndexOrThrow(TIPE)));
                mahasiswaModel.setIdguru(cursor.getInt(cursor.getColumnIndexOrThrow(NAMAGURU)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<Survey> getAllDataUser(){
        Cursor cursor = database.query(TABLE_NAME2,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<Survey> arrayList = new ArrayList<>();
        Survey mahasiswaModel;
        if (cursor.getCount()>0) {
            do {
                mahasiswaModel = new Survey();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setNamaguru(cursor.getString(cursor.getColumnIndexOrThrow(NAMAGURU)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }
    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }
    public void endTransaction(){
        database.endTransaction();
    }
    public void insertTransaction(Survey mahasiswaModel){
        String sql = "INSERT INTO "+TABLE_NAME+" ("+ PERTANYAAN +", "+ JAWAWAN +", "+ TIPE +" , "+ NAMAGURU+" ) VALUES (?, ?, ? , ? )";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getQuestion());
        stmt.bindString(2, mahasiswaModel.getAnswer());
        stmt.bindString(3, mahasiswaModel.getType());
        stmt.bindLong(4, mahasiswaModel.getIdguru());
        stmt.execute();
        stmt.clearBindings();
        Log.d("sukses", "insertTransaction: ");
    }


    public int inserttbuser(Survey mahasiswaModel){
        String sql = "INSERT INTO "+TABLE_NAME2+" ("+ NAMAGURU+" ) VALUES (?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getNamaguru());
        stmt.execute();
        stmt.clearBindings();

        String selectQuery = "SELECT * FROM "+TABLE_NAME2+" ORDER BY "+_ID+" DESC LIMIT 1;";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToLast();

        int result=cursor.getInt(cursor.getColumnIndex(_ID));
        Log.d("sukses", "insertTransaction: "+result);
        return result;


    }
}
