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

        varEtNominal = findViewById(R.id.etNominal);
        varEtCatatan = findViewById(R.id.etCatatan);
        varDpTanggal = findViewById(R.id.dpTanggal);
        varRgPilihan = findViewById(R.id.rgPilihan);
        varBtnYakin = findViewById(R.id.btnYakin);

        varBtnYakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String tanggal = dateFormat.format(calendar.getTime());

                int selectedId = varRgPilihan.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    if (selectedId == R.id.rbPendapatan) {
                        pilihan = "pendapatan";
                    } else if (selectedId == R.id.rbPengeluaran) {
                        pilihan = "pengeluaran";
                    }
                }

                DBHandler db = new DBHandler(CatatanKasActivity.this);
                if(nominal.isEmpty() || catatan.isEmpty() || tanggal.isEmpty() || pilihan.isEmpty()) {
                    Toast.makeText(CatatanKasActivity.this, "Isi semua data dengan benar"+nominal+" "+catatan+" "+tanggal+" "+pilihan, Toast.LENGTH_SHORT).show();
                } else {
                    db.addNewData(catatan,pilihan,nominal,tanggal);

                    Toast.makeText(CatatanKasActivity.this, "Data berhasil dimasukkan kedalam database", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CatatanKasActivity.this, HomeActivity.class));
                }
            }
        });

    }
}