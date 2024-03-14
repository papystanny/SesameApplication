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
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;


public class HomeFragment extends Fragment implements AdapterListHomePage.InterfacePet {

    View view;
    RecyclerView rvHomePage;
    ImageView ibHomeLock;
    TextView tvMsg;
    boolean isLocked = true;
    AdapterListHomePage.InterfacePet interfacePet;
    private List<Pet> listPet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        interfacePet = this;

        if (listPet.size() > 0) {
            listPet.clear();
        }
        getPets();

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

    private void getPets() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token
        int id = sharedPreferences.getInt("id", -1);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<List<Pet>> call = interfaceServer.getPetsByUser(authToken, id);

        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listPet = response.body();
                    rvHomePage = view.findViewById(R.id.rvHomePage);
                    rvHomePage.setHasFixedSize(true);
                    rvHomePage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    AdapterListHomePage adapterList = new AdapterListHomePage(listPet, interfacePet);
                    rvHomePage.setAdapter(adapterList);
                    Log.d("Pets", "RESPONSE : " + listPet.toString());

                } else {
                    Toast.makeText(getContext(), "BAD RESPONSE : Erreur lors de la récupération des animaux", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : Erreur lors de la récupération des animaux", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void gestionClick(int position, Pet pet) {
        Toast.makeText(getContext(), "Click on " + pet.getName(), Toast.LENGTH_SHORT).show();
    }
}
