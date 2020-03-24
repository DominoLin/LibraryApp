package com.swufe.library.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;
import com.swufe.library.LogManager;
import com.swufe.library.R;



public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration AppBarConfiguration;
    private Toolbar toolbar;
    private TextView tv_header_account, tv_header_username;
    private RecyclerView recyclerView;
//    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v, "查找图书", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
//            }
//        });

        recyclerView = findViewById(R.id.recyclerView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_user, R.id.nav_borrow, R.id.nav_collection)
                .setDrawerLayout(drawerLayout)
                .build();
        navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,AppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View navHeader = navigationView.getHeaderView(0);
        tv_header_account = navHeader.findViewById(R.id.tv_header_account);
        tv_header_username = navHeader.findViewById(R.id.tv_header_name);

        SharedPreferences sharedPreferences = getSharedPreferences(LogManager.SHARED_PREFERFEMCES_NAME, Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account","");
        String username = sharedPreferences.getString("username","");
        tv_header_account.setText(account);
        tv_header_username.setText(username);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, AppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //创建右侧菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_settings, menu);
        return true;
    }
    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            Toast.makeText(this, "Click Logout", Toast.LENGTH_SHORT).show();
            LogManager.getInstance(getApplicationContext()).logout();
        }
        return super.onOptionsItemSelected(item);
    }


}
