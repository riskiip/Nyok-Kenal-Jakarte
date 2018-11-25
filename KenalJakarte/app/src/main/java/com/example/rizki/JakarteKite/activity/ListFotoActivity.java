package com.example.rizki.JakarteKite.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rizki.JakarteKite.presenter.Foto;
import com.example.rizki.JakarteKite.adapter.ListFotoAdapter;
import com.example.rizki.JakarteKite.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListFotoActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Foto> list;
    ListFotoAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_foto);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new ListFotoAdapter(this, R.layout.fotoitem, list);
        gridView.setAdapter(adapter);

        Cursor cursor = SudutActivity.sqLiteHelper.getData("SELECT * FROM GALLERY");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String tempat = cursor.getString(2);
            String ket = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Foto(id, name, tempat, ket, image));
        }
        adapter.notifyDataSetChanged();


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListFotoActivity.this);

                dialog.setTitle("Pilih");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            //update
                            Cursor c = SudutActivity.sqLiteHelper.getData("SELECT id FROM GALLERY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }

                            showDialogUpdate(ListFotoActivity.this, arrID.get(position));
                        }
                        else if (item == 1) {
                            //delete
                            Cursor c = SudutActivity.sqLiteHelper.getData("SELECT id FROM GALLERY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogHapus(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }


    ImageView gmbviewikan;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_foto);
        dialog.setTitle("Update");

        gmbviewikan = (ImageView) dialog.findViewById(R.id.gmbviewikan);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtHarga = (EditText) dialog.findViewById(R.id.edtHarga);
        final EditText edtKer = (EditText) dialog.findViewById(R.id.edtKet);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        gmbviewikan.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("WorngConstant")
            @Override
            public void onClick(View view){
                ActivityCompat.requestPermissions(
                        ListFotoActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    SudutActivity.sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            edtHarga.getText().toString().trim(),
                            edtKer.getText().toString().trim(),
                            SudutActivity.imageViewToByte(gmbviewikan),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Berhasil",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Update Error", error.getMessage());
                }
                updateikanlist();
            }
        });




    }
    private void showDialogHapus(final int idFish){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListFotoActivity.this);

        dialogDelete.setTitle("HAPUS DATA");
        dialogDelete.setMessage("Apa Anda ingin menghapus data ?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    SudutActivity.sqLiteHelper.deleteData(idFish);
                    Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateikanlist();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateikanlist(){
        Cursor cursor = SudutActivity.sqLiteHelper.getData("SELECT * FROM GALLERY");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String tempat = cursor.getString(2);
            String ket = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Foto(id, name, tempat, ket, image));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
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

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                gmbviewikan.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
