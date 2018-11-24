package com.example.user1.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.ModelDatabase;

import pub.devrel.easypermissions.EasyPermissions;

public class Home extends AppCompatActivity {

    final int PICK_FROM_GALLERY=1001;
    EditText txtdata1;
    EditText txtdata2;
    ImageView ivku;
    Button btnsave;
    Button btnfoto;
    MahasiswaHelper mahasiswaHelper;
    ModelDatabase data;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),ListData.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mahasiswaHelper = new MahasiswaHelper(Home.this);
        ivku=(ImageView)findViewById(R.id.ivgambar);
        txtdata1 = (EditText) findViewById(R.id.txtdata1);
        txtdata2 = (EditText) findViewById(R.id.txtdata2);
        btnfoto = (Button) findViewById(R.id.btnfoto);
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (EasyPermissions.hasPermissions(Home.this, galleryPermissions)) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(i, PICK_FROM_GALLERY);
                } else {
                    EasyPermissions.requestPermissions(Home.this,"Access for storage",PICK_FROM_GALLERY,galleryPermissions);
                }




            }
        });
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mahasiswaHelper.open();
                mahasiswaHelper.beginTransaction();
                data = new ModelDatabase(txtdata1.getText().toString(), txtdata2.getText().toString());
                boolean insertreturn=mahasiswaHelper.insertTransaction(data);
                mahasiswaHelper.setTransactionSuccess();
                mahasiswaHelper.endTransaction();
                mahasiswaHelper.close();
                if (insertreturn){
                    Toast.makeText(getApplicationContext(),"sukses input",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"sukses input",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_FROM_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    ivku.setImageBitmap(yourSelectedImage);
                    /* Now you have choosen image in Bitmap format in object "yourSelectedImage". You can use it in way you want! */
                }
        }

    };
}
