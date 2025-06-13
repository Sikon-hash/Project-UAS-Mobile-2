package com.uas.mobileuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    EditText editTextName, editTextPhone, editTextEmail;
    Button btnEdit, btnSave, btnBack, btnLogout;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi view
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);

        // Disable input field di awal
        setFieldsEnabled(false);

        // Firebase init
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            // Set email ke EditText dari FirebaseAuth
            String email = currentUser.getEmail();
            editTextEmail.setText(email);

            // Ambil data user dari Firestore
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnSuccessListener(document -> {
                if (document.exists()) {
                    String name = document.getString("name");
                    String phone = document.getString("phone");
                    String emailDb = document.getString("email");

                    editTextName.setText(name);
                    editTextPhone.setText(phone);
                    editTextEmail.setText(emailDb);
                } else {
                    Toast.makeText(ProfileActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(ProfileActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            });
        }

        // Tombol Edit
        btnEdit.setOnClickListener(v -> setFieldsEnabled(true));

        // Tombol Simpan
        btnSave.setOnClickListener(v -> {
            String updatedName = editTextName.getText().toString();
            String updatedPhone = editTextPhone.getText().toString();

            if (userId != null) {
                DocumentReference userRef = db.collection("users").document(userId);
                userRef.update("name", updatedName, "phone", updatedPhone)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            setFieldsEnabled(false);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Tombol Kembali
        btnBack.setOnClickListener(v -> finish());

        // Tombol Logout
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void setFieldsEnabled(boolean enabled) {
        editTextName.setEnabled(enabled);
        editTextPhone.setEnabled(enabled);
        editTextEmail.setEnabled(false); // Email tetap tidak bisa diedit
    }
}
