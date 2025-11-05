package com.buaa.swiftgo;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton btnOpenDrawer = findViewById(R.id.btn_open_drawer);
        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        if (drawerLayout == null) {
            Toast.makeText(this, "错误：找不到 DrawerLayout (ID: drawer_layout)。请检查您的 XML 布局。", Toast.LENGTH_LONG).show();
            return;
        }

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            loadFragment(new PlaceholderFragment("主页"));
            if (navigationView.getMenu().size() > 0) {
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = Objects.requireNonNull(item.getTitle()).toString();
        Fragment selectedFragment = new PlaceholderFragment(title);

        loadFragment(selectedFragment);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public static class PlaceholderFragment extends Fragment {
        private final String title;

        public PlaceholderFragment(String title) {
            this.title = title;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public android.view.View onCreateView(@NonNull android.view.LayoutInflater inflater, android.view.ViewGroup container,
                                              android.os.Bundle savedInstanceState) {
            android.widget.TextView textView = new android.widget.TextView(getContext());
            textView.setText("这是 " + title + " 的内容");
            textView.setGravity(android.view.Gravity.CENTER);
            textView.setTextSize(24);
            return textView;
        }
    }
}
