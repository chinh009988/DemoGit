package com.example.managefood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText llemail;
    private EditText llpassword;
    private Button butonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        butonLogin = findViewById(R.id.butonLogin);
        llemail = findViewById(R.id.llemail);
        llpassword = findViewById(R.id.llpassword);

        butonLogin.setOnClickListener(v -> {
            if (llemail.getText().toString().isEmpty() || llpassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this,
                        "Mời bạn nhập email và password!", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}