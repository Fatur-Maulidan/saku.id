package com.finance.sakuid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CatatanKasActivity extends AppCompatActivity {

    private EditText varEtNominal, varEtCatatan;
    private DatePicker varDpTanggal;
    private RadioGroup varRgPilihan;
    private Button varBtnYakin;
    private String nominal, catatan, tanggal, pilihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_kas);

//      Get Data Layout
        varEtNominal = findViewById(R.id.etNominal);
        varEtCatatan = findViewById(R.id.etCatatan);
        varDpTanggal = findViewById(R.id.dpTanggal);
        varRgPilihan = findViewById(R.id.rgPilihan);
        varBtnYakin = findViewById(R.id.btnYakin);

//      Ketika Button Yakin di Klik
        varBtnYakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Mengambil data dari layout Nominal Catatan Tanggal dan Juga Pilihan (Pendapatan/Pengeluaran)
                nominal = varEtNominal.getText().toString();
                catatan = varEtCatatan.getText().toString();

                // Mendapatkan tanggal yang dipilih dari varDpTanggal
                int year = varDpTanggal.getYear();
                int month = varDpTanggal.getMonth();
                int dayOfMonth = varDpTanggal.getDayOfMonth();

                // Membuat objek Calendar
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Format tanggal menjadi string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                tanggal = dateFormat.format(calendar.getTime());

//              Cek Data mana yang diklik
                int selectedId = varRgPilihan.getCheckedRadioButtonId();

//              Ketika Pendapatan/Pengeluaran ada yang dipilih
                if(selectedId != -1) {
//                    Pilih Pendapatan
                    if (selectedId == R.id.rbPendapatan) {
                        pilihan = "pendapatan";
                    }
//                    Pilih Pengeluaran
                    else if (selectedId == R.id.rbPengeluaran) {
                        pilihan = "pengeluaran";
                    }
                }

//              Instance Database
                DBHandler db = new DBHandler(CatatanKasActivity.this);

//              Ketika ada salah satu form kosong
                if(nominal.isEmpty() || catatan.isEmpty() || tanggal.isEmpty() || pilihan.isEmpty()) {
//                  Memberikan short message bahwa data belum terisi
                    Toast.makeText(CatatanKasActivity.this, "Isi semua data dengan benar", Toast.LENGTH_SHORT).show();
                }
//              Ketika semua berhasil terisi
                else {
                    db.addNewData(catatan,pilihan,nominal,tanggal);

//                  Semua data berhasil terisi dan masuk kedalam database
                    Toast.makeText(CatatanKasActivity.this, "Data berhasil dimasukkan kedalam database", Toast.LENGTH_SHORT).show();

//                  Dipindahkan ke aktivitas Home yaitu bagian tampilan awal
                    startActivity(new Intent(CatatanKasActivity.this, HomeActivity.class));
                }
            }
        });

    }
}