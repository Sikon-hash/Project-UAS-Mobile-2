package com.uas.mobileuas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, etNama, etNomorHp;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        etNama = findViewById(R.id.namaLengkap);
        etNomorHp = findViewById(R.id.nomorHp);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String nomorHp = etNomorHp.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Masukkan Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(nomorHp)) {
                Toast.makeText(Register.this, "Isi Nama dan Nomor HP", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Simpan data tambahan ke Firebase Realtime Database
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                                String userId = user.getUid();

                                User userModel = new User(nama, nomorHp, email);
                                ref.child(userId).setValue(userModel)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(Register.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(Register.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(Register.this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    // Model class untuk user
    public static class User {
        public String name;
        public String phone;
        public String email;

        public User() {
        }

        public User(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
    }
}
