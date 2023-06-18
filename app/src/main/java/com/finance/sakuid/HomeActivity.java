package com.finance.sakuid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//      Bottom navigasi yang ada pendapatan dan pengeluaran
        bottomNavigation = findViewById(R.id.bottomNavBar);

//      Default Navigasi biar terdapat pada Pendapatan
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, new PendapatanFragment()).commit();

//      Ketika Bottom Navigasi diklik maka akan berpindah - pindah
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.homeBotNavBar:
                        selectedFragment = new PendapatanFragment();
                        break;
                    case R.id.bantuanBotNavBar:
                        selectedFragment = new PengeluaranFragment();
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerFragment, selectedFragment).commit();
                return true;
            }
        });
    }
}