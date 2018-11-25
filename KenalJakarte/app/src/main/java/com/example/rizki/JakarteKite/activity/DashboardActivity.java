package com.example.rizki.JakarteKite.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rizki.JakarteKite.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnKenalan, btnSudut, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        kenalan();
        sudut();
        logout();
    }

    private void sudut() {
        btnSudut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent (DashboardActivity.this, SudutActivity.class);
                startActivity(a);
            }
        });
    }

    private void kenalan() {
        btnKenalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(b);
            }
        });
    }

    private void init() {
        btnKenalan = (Button) findViewById(R.id.btnKenalan);
        btnSudut = (Button) findViewById(R.id.btnSudut);
        btnLogout = (Button) findViewById(R.id.btnLogout);
    }

    private void logout() {
        btnLogout.setOnClickListener(view -> showAlertDialog());
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Apa kalian ingin Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deletePreference();
                        Intent login = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deletePreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        preferences.edit().remove("username").commit();
        preferences.edit().remove("password").commit();
    }
}