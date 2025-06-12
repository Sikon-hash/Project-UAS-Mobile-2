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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    EditText editTextName, editTextPhone, editTextEmail;
    Button btnEdit, btnSave, btnBack, btnLogout;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

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
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            // Set email ke EditText dari FirebaseAuth
            String email = currentUser.getEmail();
            editTextEmail.setText(email);

            // Ambil data user dari Realtime Database
            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);

                        editTextName.setText(name);
                        editTextPhone.setText(phone);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Tombol Edit
        btnEdit.setOnClickListener(v -> setFieldsEnabled(true));

        // Tombol Simpan
        btnSave.setOnClickListener(v -> {
            String updatedName = editTextName.getText().toString();
            String updatedPhone = editTextPhone.getText().toString();

            if (userId != null) {
                DatabaseReference userRef = reference.child(userId);
                userRef.child("name").setValue(updatedName);
                userRef.child("phone").setValue(updatedPhone);
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                setFieldsEnabled(false);
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
        // Email tetap tidak bisa diedit
        editTextEmail.setEnabled(false);
    }
}
