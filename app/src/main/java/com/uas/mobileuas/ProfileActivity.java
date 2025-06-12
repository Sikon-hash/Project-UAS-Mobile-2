package com.uas.mobileuas;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText ;
    private Button editButton, saveButton, logoutButton, backButton;
    private boolean isEditMode = false;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        phoneEditText = findViewById(R.id.editTextPhone);
        editButton = findViewById(R.id.btnEdit);
        saveButton = findViewById(R.id.btnSave);
        logoutButton = findViewById(R.id.btnLogout);
        backButton = findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            nameEditText.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "");
            emailEditText.setText(currentUser.getEmail());
        }

        nameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        saveButton.setVisibility(View.GONE);

        editButton.setOnClickListener(v -> {
            isEditMode = true;
            nameEditText.setEnabled(true);
            emailEditText.setEnabled(true);
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
        });

        saveButton.setOnClickListener(v -> {
            String newEmail = emailEditText.getText().toString().trim();
            String newName = nameEditText.getText().toString().trim();

            if (currentUser != null && !newEmail.equals(currentUser.getEmail())) {
                currentUser.updateEmail(newEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Email diperbarui", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Gagal update email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            // (Opsional) Update display name
            // Kamu bisa menggunakan FirebaseUser.updateProfile atau simpan nama di database lain.

            nameEditText.setEnabled(false);
            emailEditText.setEnabled(false);
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());
    }
}
