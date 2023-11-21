package com.example.intisuperapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intisuperapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    public static final int ADD_NOTE_REQUEST = 1;

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityMainBinding binding;

    private NavController navController;

    private NavController.OnDestinationChangedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.profileFragment).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        binding.intiSuperappLogo.setOnClickListener(v -> navController.navigate(R.id.homeFragment));
        binding.navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.appointmentsFragment) {
                navController.navigate(R.id.appointmentsFragment);
            } else if (itemId == R.id.bookingsFragment) {
                navController.navigate(R.id.bookingsFragment);
            } else if (itemId == R.id.eventMainFragment) {
                navController.navigate(R.id.eventMainFragment);
            } else if (itemId == R.id.lostAndFoundFragment) {
                navController.navigate(R.id.lostAndFoundFragment);
            } else if (itemId == R.id.createBookingsFragment) {
                navController.navigate(R.id.createBookings);
            } else if (itemId == R.id.addAppointmentsFragment) {
                navController.navigate(R.id.addAppointmentsFragment);
            } else if (itemId == R.id.createBookingsFragment) {
                navController.navigate(R.id.createBookingsFragment);
            } else if (itemId == R.id.loginFragment) {
                setDrawerEnabled(false);
                navController.navigate(R.id.loginFragment);
            } else if (itemId == R.id.profileFragment) {
                navController.navigate(R.id.profileFragment);
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.eventMainFragment) {
                navController.navigate(R.id.eventMainFragment);
            } else if (itemId == R.id.appointmentsFragment) {
                navController.navigate(R.id.appointmentsFragment);
            } else if (itemId == R.id.bookingsFragment) {
                navController.navigate(R.id.bookingsFragment);
            } else if (itemId == R.id.lostAndFoundFragment) {
                navController.navigate(R.id.lostAndFoundFragment);
            }
            return true;
        });
        //remove top action bar and bottom navigation bar in login and registration
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registrationFragment || destination.getId() == R.id.roleRegistrationFragment) {
                binding.bottomNavigationView.setVisibility(View.GONE);
                getSupportActionBar().hide();
                setDrawerEnabled(false);
            } else if (destination.getId() == R.id.homeFragment) {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
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
}