package com.finance.sakuid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Class ini digunakan untuk menampilkan data pada Pendapatan
public class MyAdapterPengeluaran extends RecyclerView.Adapter<MyAdapterPengeluaran.MyViewHolder> {
    private Context context;
    private ArrayList<String> catatan;
    private ArrayList<String> nominal;
    private ArrayList<String> tanggal;

    public MyAdapterPengeluaran(Context context, ArrayList catatan, ArrayList nominal, ArrayList tanggal) {
        this.context = context;
        this.catatan = catatan;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_pengeluaran,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.catatan.setText(String.valueOf(catatan.get(position)));
        holder.nominal.setText(String.valueOf(nominal.get(position)));
        holder.tanggal.setText(String.valueOf(tanggal.get(position)));
    }

    @Override
    public int getItemCount() {
        return catatan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView catatan, nominal, tanggal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catatan = itemView.findViewById(R.id.tvCatatan);
            nominal = itemView.findViewById(R.id.tvNominal);
            tanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
