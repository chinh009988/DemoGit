package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //    private String username, password;
//    private EditText llemail;
//    private EditText llfullname;
//    private EditText llpassword;
//    private EditText llrepassword;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBox;
    private TextView buttonforget;
    private TextView buttonSignup;
    private Button btnLogin;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    public static String user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonforget = findViewById(R.id.buttonforget);
        buttonSignup = findViewById(R.id.butonSignup);
        btnLogin = findViewById(R.id.butonLogin);
        auth = FirebaseAuth.getInstance();
        checkBox = findViewById(R.id.checkbox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            checkBox.setChecked(true);
            checkBox.setChecked(true);
        }


        buttonSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        buttonforget.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, dialog_forgot.class);
            startActivity(intent);
        });


        btnLogin.setOnClickListener(v -> {
            String email = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            if (editTextUsername.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Không được để trống!", Toast.LENGTH_LONG).show();
                return;
            }
            if (checkBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", email);
                loginPrefsEditor.putString("password", password);
                loginPrefsEditor.commit();

            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();

            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công ", Toast.LENGTH_LONG).show();
                        user = email;
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Sai toàn khoản hoặc mật khẩu!!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }


}