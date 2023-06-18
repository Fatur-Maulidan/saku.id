package com.finance.sakuid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendapatanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendapatanFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<String> varCatatan, varNominal, varTanggal;
    DBHandler db;
    MyAdapterPendapatan adapter;

    public PendapatanFragment() {
        // Required empty public constructor
    }


    public static PendapatanFragment newInstance(String param1, String param2) {
        PendapatanFragment fragment = new PendapatanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendapatan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView varBtnTambah = view.findViewById(R.id.btnIconTambah);

//      Instance Object
        db = new DBHandler(getActivity());
        varCatatan = new ArrayList<>();
        varNominal = new ArrayList<>();
        varTanggal = new ArrayList<>();

//      Get Data From Layout
        recyclerView = view.findViewById(R.id.recyclerViewPendapatan);
        adapter = new MyAdapterPendapatan(getActivity(),varCatatan,varNominal,varTanggal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        displayData();

//      Show Jumlah Saldo
        TextView jmlhSaldo = view.findViewById(R.id.tvJumlahSaldo);
        jmlhSaldo.setText(String.valueOf(db.sumData()));
//      End Show Jumlah Saldo

//      Ketika button tambah di klik
        varBtnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CatatanKasActivity.class));
            }
        });
    }

//  Munculkan semua data dari database kedalam tampilan
    private void displayData(){
        Cursor cursor = db.getData("pendapatan");
        if(cursor.getCount()==0){
            Toast.makeText(getActivity(), "Tidak ada Data", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while(cursor.moveToNext()){
                varCatatan.add(cursor.getString(1));
                varNominal.add(cursor.getString(3));
                varTanggal.add(cursor.getString(4));
            }
        }
    }
}