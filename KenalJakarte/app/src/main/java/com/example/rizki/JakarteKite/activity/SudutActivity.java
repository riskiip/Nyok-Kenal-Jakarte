package com.example.rizki.JakarteKite.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rizki.JakarteKite.R;
import com.example.rizki.JakarteKite.model.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SudutActivity extends AppCompatActivity {

    EditText txtnama, txtharga,txtket;
    Button btnAdd,btnPilih,btnList;
    ImageView gmbikan;

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudut);

        sqLiteHelper = new SQLiteHelper(this, "GalleryDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS GALLERY(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, tempat VARCHAR, ket VARCHAR, image BLOB)");

        init();
        pilihgmr();
        isidata();
        listnya();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                gmbikan.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void ijinpoto(){
        ActivityCompat.requestPermissions(
                SudutActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
    }

    public void inputdata(){
        try{
            sqLiteHelper.insertData(
                    txtnama.getText().toString().trim(),
                    txtharga.getText().toString().trim(),
                    txtket.getText().toString().trim(),
                    imageViewToByte(gmbikan)
            );
            Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show();
            txtnama.setText("");
            txtharga.setText("");
            txtket.setText("");
            gmbikan.setImageResource(R.drawable.camera);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void init(){
        txtnama = (EditText) findViewById(R.id.txtnama);
        txtharga = (EditText) findViewById(R.id.txtharga);
        txtket = (EditText) findViewById(R.id.txtket);
        btnPilih = (Button) findViewById(R.id.btnPilih);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnList = (Button) findViewById(R.id.btnList);

        gmbikan = (ImageView) findViewById(R.id.gmbikan);

    }
    private void pilihgmr(){
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WorngConstant")
            @Override
            public void onClick(View v) {
            ijinpoto();
            }
        });

    }
    private void isidata(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               inputdata();
            }
        });
    }
    private void listnya(){
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(SudutActivity.this, ListFotoActivity.class);
            startActivity(intent);
            }
        });
    }

}
