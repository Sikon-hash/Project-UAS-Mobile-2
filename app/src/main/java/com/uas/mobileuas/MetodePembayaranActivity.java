package com.uas.mobileuas;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MetodePembayaranActivity extends AppCompatActivity {

    private Spinner spinnerMetode;
    private TextView textKeterangan;
    private EditText editTextNoHp;
    private Button btnBayar;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

        spinnerMetode = findViewById(R.id.spinnerMetode);
        textKeterangan = findViewById(R.id.textKeterangan);
        editTextNoHp = findViewById(R.id.editTextNoHp);
        btnBayar = findViewById(R.id.btnBayarSekarang);

        firestore = FirebaseFirestore.getInstance();

        editTextNoHp.setVisibility(View.GONE);

        String[] metodePembayaran = {
                "Pilih Metode Pembayaran",
                "Transfer Bank",
                "E-Wallet (OVO, Dana, Gopay)",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, metodePembayaran);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetode.setAdapter(adapter);

        spinnerMetode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        textKeterangan.setText("Silakan transfer ke Bank:");
                        editTextNoHp.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        textKeterangan.setText("Silakan scan QR di OVO/Dana/Gopay.");
                        editTextNoHp.setVisibility(View.VISIBLE);
                        break;
                    default:
                        textKeterangan.setText("");
                        editTextNoHp.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textKeterangan.setText("");
                editTextNoHp.setVisibility(View.GONE);
            }
        });

        btnBayar.setOnClickListener(v -> {
            String nomorHp = editTextNoHp.getText().toString().trim();
            int selectedMetode = spinnerMetode.getSelectedItemPosition();

            if (selectedMetode == 0) {
                Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nomorHp.isEmpty()) {
                Toast.makeText(this, "Masukkan nomor HP", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat data yang akan dikirim ke Firestore
            Map<String, Object> data = new HashMap<>();
            data.put("nomorHp", nomorHp);
            data.put("metode", metodePembayaran[selectedMetode]);
            data.put("timestamp", System.currentTimeMillis());

            firestore.collection("pembayaran")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Data pembayaran terkirim", Toast.LENGTH_SHORT).show();
                        editTextNoHp.setText("");
                        spinnerMetode.setSelection(0);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
