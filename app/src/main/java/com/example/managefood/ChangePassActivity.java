package com.example.managefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassActivity extends AppCompatActivity {
    private EditText llemail;
    private EditText llpassword;
    private Button buttonchange;
    private Button buttoncancle;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_forgot);
        llemail = findViewById(R.id.llemail);
        llpassword = findViewById(R.id.llpassword);
        buttonchange = findViewById(R.id.buttonchange);
        buttoncancle = findViewById(R.id.buttoncancle);
        auth = FirebaseAuth.getInstance();
        buttoncancle.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buttonchange.setOnClickListener(v -> {
            String email = llemail.getText().toString();
            if (llemail.getText().toString().isEmpty()) {
                Toast.makeText(ChangePassActivity.this, "Email không được để trống!", Toast.LENGTH_LONG).show();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePassActivity.this, "Kiểm tra email để lấy lại mật khẩu", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ChangePassActivity.this, "Email này không tồn tại !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}