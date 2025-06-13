package com.uas.mobileuas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        String kursi = getIntent().getStringExtra("kursi");

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
        daftarBus.add("Harapan Indah - 10.00 WIB - Rp145.000");
        daftarBus.add("Eka Mira - 15.00 WIB - Rp150.000");
        daftarBus.add("Restu - 13.00 WIB - Rp140.000");
        daftarBus.add("Harapab Baru - 11.00 WIB - Rp120.000");

        adapter = new JadwalBusAdapter(daftarBus);
        recyclerView.setAdapter(adapter);

        ListView listView = findViewById(R.id.listViewBus); // pastikan ada di layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftarBus);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pilihanBus = daftarBus.get(position);
                Intent intent = new Intent(JadwalBusActivity.this, PembayaranActivity.class);
                intent.putExtra("dataBus", pilihanBus);
                startActivity(intent);
            }
        });

    }
}