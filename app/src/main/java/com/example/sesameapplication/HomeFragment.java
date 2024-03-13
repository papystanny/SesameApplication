package com.example.sesameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListHomePage;
import unique.Pet;


public class HomeFragment extends Fragment implements AdapterListHomePage.InterfacePet {

    View view;
    RecyclerView rvHomePage;
    ImageView ibHomeLock;
    TextView tvMsg;
    boolean isLocked = true;
    public static List<Pet> listPet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (listPet.size() > 0) {
            listPet.clear();
        }
        listPet.add(new Pet("Rex", "Rexou", "Chien", "ic_dog", "123456", true));
        listPet.add(new Pet("Mina", "Minou", "Chat", "ic_dog", "789456", false));
        Log.d("ListPet", listPet.toString());
        rvHomePage = view.findViewById(R.id.rvHomePage);
        rvHomePage.setHasFixedSize(true);
        rvHomePage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        AdapterListHomePage adapterList = new AdapterListHomePage(listPet, this);
        rvHomePage.setAdapter(adapterList);




        // Appeler la méthode pour afficher les SharedPreferences
        displaySharedPreferences();

        // Récupération des éléments de la vue
        ibHomeLock = view.findViewById(R.id.ibHomeLock);
        tvMsg = view.findViewById(R.id.tvMsg);
        ibHomeLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocked) {
                    ibHomeLock.setImageResource(R.drawable.close);
                    tvMsg.setText("La porte est verrouillée");

                } else {
                    ibHomeLock.setImageResource(R.drawable.open);
                    tvMsg.setText("La porte est déverrouillée");
                }
                isLocked = !isLocked;
            }
        });

        return view;
    }

    private void displaySharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Récupération des données
        String token = sharedPreferences.getString("token", "Pas de token trouvé");
        int id = sharedPreferences.getInt("id", -1); // -1 sera la valeur par défaut si "id" n'est pas trouvé
        String firstname = sharedPreferences.getString("firstname", "Pas de prénom trouvé");
        String lastname = sharedPreferences.getString("lastname", "Pas de nom trouvé");
        String phone = sharedPreferences.getString("phone", "Pas de téléphone trouvé");
        String email = sharedPreferences.getString("email", "Pas d'email trouvé");

        // Affichage des données récupérées dans le logcat
        Log.d("SharedPreferences", "Token: " + token);
        Log.d("SharedPreferences", "ID: " + id);
        Log.d("SharedPreferences", "Firstname: " + firstname);
        Log.d("SharedPreferences", "Lastname: " + lastname);
        Log.d("SharedPreferences", "Phone: " + phone);
        Log.d("SharedPreferences", "Email: " + email);
    }

    @Override
    public void gestionClick(int position, Pet pet) {
        Toast.makeText(getContext(), "Click on " + pet.getName(), Toast.LENGTH_SHORT).show();
    }
}
