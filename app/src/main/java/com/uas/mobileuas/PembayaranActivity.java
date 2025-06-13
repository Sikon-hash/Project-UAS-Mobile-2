package com.uas.mobileuas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PembayaranActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        String dataBus = getIntent().getStringExtra("dataBus");
        TextView textView = findViewById(R.id.textViewBusDipilih);
        textView.setText("Anda memilih: " + dataBus);

        Button btnLanjutBayar = findViewById(R.id.btnLanjutBayar);
        btnLanjutBayar.setOnClickListener(v -> {
            Intent intent = new Intent(PembayaranActivity.this, MetodePembayaranActivity.class);
            startActivity(intent);
        });

    }
}