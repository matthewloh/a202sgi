package com.example.intisuperapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intisuperapp.OldNotes.NoteViewModel;
import com.example.intisuperapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    public static final int ADD_NOTE_REQUEST = 1;

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityMainBinding binding;

    private NoteViewModel noteViewModel;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private NavController.OnDestinationChangedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    binding.drawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//                    finish();
//                }
//            }
//        });
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.profileFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        binding.intiSuperappLogo.setOnClickListener(v -> navController.navigate(R.id.homeFragment));
        binding.navView.setNavigationItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    Toast.makeText(this, "Item ID: " + itemId, Toast.LENGTH_SHORT).show();
                    if (itemId == R.id.homeFragment) {
                        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                    } else if (itemId == R.id.loginFragment) {
                        setDrawerEnabled(false);
                        navController.navigate(R.id.loginFragment);
                    }
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
        );
        binding.bottomFabHome.setOnClickListener(v -> navController.navigate(R.id.homeFragment));
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.events) {
                navController.navigate(R.id.eventsFragment);
            } else if (itemId == R.id.profile) {
                navController.navigate(R.id.profileFragment);
            } else if (itemId == R.id.bookings) {
                navController.navigate(R.id.bookingsFragment);
            }
            return true;
        });

        //remove top action bar and bottom navigation bar in login and registration
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registrationFragment || destination.getId() == R.id.roleRegistrationFragment) {
                binding.coordinatorLayout.setVisibility(View.GONE);
                getSupportActionBar().hide();
                setDrawerEnabled(false);
            } else if (destination.getId() == R.id.homeFragment) {
                binding.coordinatorLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
                setDrawerEnabled(true);
            } else if (destination.getId() == R.id.createBookingsFragment) {
                setDrawerEnabled(false);
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
            } else {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        binding.drawerLayout.setDrawerLockMode(lockMode);
    }

//    @Override
//    public void showHamburgerIcon() {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toggle.setDrawerIndicatorEnabled(true);
//        toggle.syncState();
//    }
//
//    @Override
//    public void showBackIcon() {
//        toggle.setDrawerIndicatorEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toggle.syncState();
//    }
}