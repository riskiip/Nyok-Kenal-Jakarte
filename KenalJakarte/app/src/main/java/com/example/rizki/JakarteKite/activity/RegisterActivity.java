package com.example.rizki.JakarteKite.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.rizki.JakarteKite.model.DatabaseHandler;
import com.example.rizki.JakarteKite.R;
import com.example.rizki.JakarteKite.presenter.User;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private User usermodel;
    private EditText etUsernameReg, etPasswordReg;
    private Button btReg;
    private String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        register();
    }

    private void initView() {
        etUsernameReg = findViewById(R.id.etUsernameReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        btReg = findViewById(R.id.btnReg);
    }

    private void initUser(){
        username = etUsernameReg.getText().toString();
        password = etPasswordReg.getText().toString();

        usermodel = new User();
        usermodel.setUsername(username);
        usermodel.setPassword(password);
    }

    private void initDataHandler(){
        initUser();

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.addUser(usermodel);
        User model = databaseHandler.getMahasiswa(1);
        Log.e("record", model.getUsername().toString());
        Intent balik = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(balik);
        finish();
    }

    private void register() {
        btReg.setOnClickListener(V -> initDataHandler());
    }
}