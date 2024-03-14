package com.example.sesameapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListHomePage;
import unique.Pet;

public class MainActivity extends AppCompatActivity {

    ImageButton biHome, biList, biPets, biSettings, biSchedule;
    private LinearLayout linearLayout;
    private List<Pet> list = new ArrayList<>();
    RecyclerView rvHomePage;
    Context context;
    AdapterListHomePage adapterList;
    FragmentManager fragmentManager;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);

        context = this;

        // gestion fragment
        biHome = findViewById(R.id.ibHome);
        biList = findViewById(R.id.ibList);
        biPets = findViewById(R.id.ibPets);
        biSettings = findViewById(R.id.ibSettings);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Ajout d'un listener pour écouter les changements de destination
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.fragment_login || destination.getId() == R.id.fragment_create_account || destination.getId() == R.id.fragment_email_recup || destination.getId() == R.id.fragment_email_recup_code || destination.getId() == R.id.fragment_password_recup) {
                    // Si nous sommes dans fragment_login, cachez le LinearLayout
                    linearLayout.setVisibility(View.GONE);
                }
                else
                    linearLayout.setVisibility(View.VISIBLE);
            });
        }
        // Configuration du NavHostFragment et du NavController

        if (navHostFragment != null) {
             navController = navHostFragment.getNavController();
        }


        // Listeners pour les boutons de navigation
        biHome.setOnClickListener(view -> { navigateUsingNavController(R.id.fragment_home); resetButtonColor(); biHome.setColorFilter(Color.parseColor("#FF5C00"));});
        biList.setOnClickListener(view -> { navigateUsingNavController(R.id.petActivityFragment2); resetButtonColor(); biList.setColorFilter(Color.parseColor("#FF5C00"));});
        biPets.setOnClickListener(view -> { navigateUsingNavController(R.id.petsFragment3); resetButtonColor(); biPets.setColorFilter(Color.parseColor("#FF5C00"));});
        biSettings.setOnClickListener(view -> { navigateUsingNavController(R.id.fragment_settings); resetButtonColor(); biSettings.setColorFilter(Color.parseColor("#FF5C00"));});
    }

    private void resetButtonColor() {
        biHome.setColorFilter(Color.parseColor("#2B2B2B"));
        biList.setColorFilter(Color.parseColor("#2B2B2B"));
        biPets.setColorFilter(Color.parseColor("#2B2B2B"));
        biSettings.setColorFilter(Color.parseColor("#2B2B2B"));
    }

    private void navigateUsingNavController(int actionId) {
        if (navController != null) {
            navController.navigate(actionId);
        }
    }

    // L méthode pour naviguer d'un fragment à un autre créait un problème. le fragment home
    // était toujours là et ne disparaissait jamais alors j'ai enlevé cette méthode pour faire que les fragments s'échange avec le navhost 

}