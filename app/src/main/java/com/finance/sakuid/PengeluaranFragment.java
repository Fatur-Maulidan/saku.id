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
 * Use the {@link PengeluaranFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PengeluaranFragment extends Fragment {


    private RecyclerView recyclerView;
    ArrayList<String> varCatatan, varNominal, varTanggal;
    DBHandler db;
    MyAdapterPengeluaran adapter;

    public PengeluaranFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PengeluaranFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PengeluaranFragment newInstance(String param1, String param2) {
        PengeluaranFragment fragment = new PengeluaranFragment();
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
        return inflater.inflate(R.layout.fragment_pengeluaran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView varBtnTambah = view.findViewById(R.id.btnIconTambah);

        db = new DBHandler(getActivity());
        varCatatan = new ArrayList<>();
        varNominal = new ArrayList<>();
        varTanggal = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerViewPengeluaran);
        adapter = new MyAdapterPengeluaran(getActivity(),varCatatan,varNominal,varTanggal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        displayData();

        TextView jmlhSaldo = view.findViewById(R.id.tvJumlahSaldo);
        jmlhSaldo.setText(String.valueOf(db.sumData()));

        varBtnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CatatanKasActivity.class));
            }
        });
    }

    private void displayData(){
        Cursor cursor = db.getData("pengeluaran");
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