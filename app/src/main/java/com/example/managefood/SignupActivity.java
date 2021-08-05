package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private Button buttonSignup;
    private EditText llfullname;
    private EditText llemail;
    private EditText llpassword;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        buttonSignup = findViewById(R.id.buttonSignup);
        llemail = findViewById(R.id.llemail);
        llpassword = findViewById(R.id.llpassword);
        auth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(v -> {

            String email = llemail.getText().toString();
            String password = llpassword.getText().toString();

            if (llemail.getText().toString().isEmpty() || llpassword.getText().toString().isEmpty()) {
                Toast.makeText(SignupActivity.this,
                        "Không được để trống!", Toast.LENGTH_LONG).show();
            } else {
                registerUser(email, password);
            }
        });

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this,
                            "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this,
                            "Đăng ký không thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}