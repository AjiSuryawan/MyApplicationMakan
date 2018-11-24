package com.example.user1.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.ModelDatabase;

public class Home extends AppCompatActivity {

    EditText txtdata1;
    EditText txtdata2;
    Button btnsave;
    ImageView ivImage;
    TextView tvUploadImage;
    MahasiswaHelper mahasiswaHelper;
    ModelDatabase data;

    Uri selectedImage;

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

        txtdata1 = (EditText) findViewById(R.id.txtdata1);
        txtdata2 = (EditText) findViewById(R.id.txtdata2);
        tvUploadImage = findViewById(R.id.uploadImage);
        ivImage = findViewById(R.id.ivImage);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BitmapDrawable drawable = (BitmapDrawable) ivImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    tvUploadImage.setText(bitmap.toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mahasiswaHelper.open();
                mahasiswaHelper.beginTransaction();

                data = new ModelDatabase(txtdata1.getText().toString(), txtdata2.getText().toString(), selectedImage.toString());

                mahasiswaHelper.insertTransaction(data);
                mahasiswaHelper.setTransactionSuccess();
                mahasiswaHelper.endTransaction();
                mahasiswaHelper.close();
            }
        });
        tvUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 234);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 234 && resultCode == Activity.RESULT_OK){
            selectedImage = data.getData();
            tvUploadImage.setText(selectedImage.toString());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ivImage.setImageBitmap(bitmap);
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
