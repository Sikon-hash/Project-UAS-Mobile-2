package com.uas.mobileuas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JadwalBusAdapter extends RecyclerView.Adapter<JadwalBusAdapter.ViewHolder> {

    private List<String> listBus;

    public JadwalBusAdapter(List<String> listBus) {
        this.listBus = listBus;
    }

    @NonNull
    @Override
    public JadwalBusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jadwal_bus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalBusAdapter.ViewHolder holder, int position) {
        holder.tvJadwal.setText(listBus.get(position));
    }

    @Override
    public int getItemCount() {
        return listBus.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJadwal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJadwal = itemView.findViewById(R.id.tvJadwal);
        }
    }
}
