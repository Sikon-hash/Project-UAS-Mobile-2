package com.uas.mobileuas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JadwalBusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> daftarBus;
    JadwalBusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_bus);

        // Ambil data dari Intent
        String dari = getIntent().getStringExtra("dari");
        String ke = getIntent().getStringExtra("ke");
        String tanggal = getIntent().getStringExtra("tanggal");

        TextView tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText("Hasil untuk: " + dari + " → " + ke + " pada " + tanggal);

        recyclerView = findViewById(R.id.recyclerJadwal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data jadwal
        daftarBus = new ArrayList<>();
        daftarBus.add("PO Haryanto - 08.00 WIB - Rp120.000");
        daftarBus.add("Sinar Jaya - 09.30 WIB - Rp110.000");
        daftarBus.add("Rosalia Indah - 12.00 WIB - Rp130.000");
        daftarBus.add("DAMRI - 14.00 WIB - Rp100.000");

        adapter = new JadwalBusAdapter(daftarBus);
        recyclerView.setAdapter(adapter);
    }
}
