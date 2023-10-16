package com.example.intisuperapp;

import android.os.Bundle;

import com.example.intisuperapp.OldNotes.NoteViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;

import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intisuperapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityMainBinding binding;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.events) {
                navController.navigate(R.id.eventsFragment);
            } else if (itemId == R.id.profile) {
                navController.navigate(R.id.profileFragment);
            } else if (itemId == R.id.notifications) {
                navController.navigate(R.id.notificationsFragment);
            }
            return true;
        });

//        //remove top action bar in login and registration
//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registrationFragment) {
//                getSupportActionBar().hide();
//            } else {
//                getSupportActionBar().show();
//            }
//        });

        //remove top action bar and bottom navigation bar in login and registration
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.loginFragment || destination.getId()==R.id.registrationFragment) {
                    bottomNavigationView.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    getSupportActionBar().show();
                }
            }
        });





        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete_all_notes) {
                    noteViewModel.deleteAllNotes();
                    Toast.makeText(MainActivity.this, "All notes deleted", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }


        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.delete_all_notes) {
//            noteViewModel.deleteAllNotes();
//            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


}