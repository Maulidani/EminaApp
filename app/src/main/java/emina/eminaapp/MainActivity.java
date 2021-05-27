package emina.eminaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import emina.eminaapp.ui.fragment.AccountFragment;
import emina.eminaapp.ui.fragment.HomeFragment;
import emina.eminaapp.ui.fragment.LocationFragment;
import emina.eminaapp.ui.fragment.NewsFragment;
import emina.eminaapp.ui.fragment.ProductsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigasi);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navHome:
                fragment = new HomeFragment();
                break;
            case R.id.navProduct:
                fragment = new ProductsFragment();
                break;
//            case R.id.navLocation:
//                fragment = new LocationFragment();
//                break;
            case R.id.navNews:
                fragment = new NewsFragment();
                break;
            case R.id.navAccount:
                fragment = new AccountFragment();
                break;
        }
        return loadFragment(fragment);
    }
}