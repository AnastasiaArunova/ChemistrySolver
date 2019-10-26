package com.chemistrysolver;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// активность, отображающаяся при запуске приложения
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // создание выдвижного меню
        NavigationView navigationView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        // установка фрагмента, который отображается при запуске
        Fragment fragment = new MainField();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        setTitle("Главная");

        navigationView.setNavigationItemSelectedListener(this);

        // установка заголовка выдвижного меню
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.chemistrysolver);
        navName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Candara.ttf"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // id пункта, на который нажали
        int id = item.getItemId();
        // Фрагмент, который будет отображаться после нажатия
        Fragment fragment = null;
        // фрагмент принимает значение в зависимости от того, на что нажали
        if (id == R.id.nav_main) {
            fragment = new MainField();
        } else if (id == R.id.nav_help) {
            fragment = new InfoHelpField();
            ((InfoHelpField) fragment).stringResource = R.string.help;
            ((InfoHelpField) fragment).resource = R.layout.information_field;
        } else if (id == R.id.nav_info) {
            fragment = new InfoHelpField();
            ((InfoHelpField) fragment).stringResource = R.string.information;
            ((InfoHelpField) fragment).resource = R.layout.information_field;
        } else if (id == R.id.nav_develop){
            fragment = new InfoHelpField();
            ((InfoHelpField) fragment).stringResource = R.string.develop;
            ((InfoHelpField) fragment).resource = R.layout.develop_field;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // смена фрагмента после нажатия
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
            // смена заголовка в зависимости от того, на что нажали
            setTitle(item.getTitle());
        }

        return true;
    }
}
