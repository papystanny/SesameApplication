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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new HomeFragment())
                .commit();

        context = this;

        // gestion fragment
        biHome = findViewById(R.id.ibHome);
        biList = findViewById(R.id.ibList);
        biPets = findViewById(R.id.ibPets);
        biSettings = findViewById(R.id.ibSettings);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
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

    private void resetButtonColor() {
        biHome.setColorFilter(Color.parseColor("#2B2B2B"));
        biList.setColorFilter(Color.parseColor("#2B2B2B"));
        biPets.setColorFilter(Color.parseColor("#2B2B2B"));
        biSettings.setColorFilter(Color.parseColor("#2B2B2B"));
    }

    public void replaceFragment(View v) {
        resetButtonColor();

        if (v.getId() == R.id.ibHome)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new HomeFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
            biHome.setColorFilter(Color.parseColor("#FF5C00"));
        }

        if (v.getId() == R.id.ibList)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new PetActivityFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
            biList.setColorFilter(Color.parseColor("#FF5C00"));
        }

        if (v.getId() == R.id.ibSettings)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new SettingsFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
            biSettings.setColorFilter(Color.parseColor("#FF5C00"));
        }

        if (v.getId() == R.id.ibPets)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new PetsFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
            biPets.setColorFilter(Color.parseColor("#FF5C00"));
        }

        if (v.getId() == R.id.ibSchedule)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new LockScheduleFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
        }

        if (v.getId() == R.id.ibAddSchedule)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new CreateScheduleFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();

        }

        if (v.getId() == R.id.ibAddPet)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new AddPetsFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
        }

        if (v.getId() == R.id.bProfilSetting)
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,new ProfileFragment());
            fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière avec le bouton de retour
            fragmentTransaction.commit();
        }
    }
}