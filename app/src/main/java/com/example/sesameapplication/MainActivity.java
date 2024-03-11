package com.example.sesameapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Ajout d'un listener pour écouter les changements de destination
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                // Vérifiez si la destination actuelle est fragment_login
                if (destination.getId() == R.id.fragment_login || destination.getId() == R.id.fragment_create_account || destination.getId() == R.id.fragment_email_recup || destination.getId() == R.id.fragment_email_recup_code || destination.getId() == R.id.fragment_password_recup) {
                    // Si nous sommes dans fragment_login, cachez le LinearLayout
                    linearLayout.setVisibility(View.GONE);
                }
                else
                    linearLayout.setVisibility(View.VISIBLE);
            });
        }
    }
}