package com.uas.mobileuas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    EditText etDari, etKe, etTanggalPergi, etTanggalPulang;
    Button btnCariBus, btnPesanSekarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etDari = findViewById(R.id.etDari);
        etKe = findViewById(R.id.etKe);
        etTanggalPergi = findViewById(R.id.etTanggalPergi);
        etTanggalPulang = findViewById(R.id.etTanggalPulang);
        btnCariBus = findViewById(R.id.btnCariBus);
        btnPesanSekarang = findViewById(R.id.btnPesanSekarang);

        etTanggalPergi.setOnClickListener(v -> showDateDialog(etTanggalPergi));
        etTanggalPulang.setOnClickListener(v -> showDateDialog(etTanggalPulang));

        btnCariBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dari = etDari.getText().toString();
                String ke = etKe.getText().toString();
                String tanggal = etTanggalPergi.getText().toString();

                if (dari.isEmpty() || ke.isEmpty() || tanggal.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(HomeActivity.this, JadwalBusActivity.class);
                    intent.putExtra("dari", dari);
                    intent.putExtra("ke", ke);
                    intent.putExtra("tanggal", tanggal);
                    startActivity(intent);
                }
            }
        });

        btnPesanSekarang.setOnClickListener(v ->
                Toast.makeText(this, "Fitur Pesan Sekarang ditekan", Toast.LENGTH_SHORT).show()
        );
    }

    private void showDateDialog(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String tanggal = dayOfMonth + "/" + (month + 1) + "/" + year;
                    targetEditText.setText(tanggal);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
