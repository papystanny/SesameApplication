package com.example.sesameapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListPet;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;


public class PetsFragment extends Fragment implements AdapterListPet.InterfacePet {

    RecyclerView rvPets;
    AdapterListPet adapterListPet;
    List<Pet> listPet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pets, container, false);

        if (listPet.size() > 0) {
            listPet.clear();
        }
        getPets();
        rvPets = view.findViewById(R.id.rvPets);
        rvPets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPets.setHasFixedSize(true);
        adapterListPet = new AdapterListPet(listPet, this);
        rvPets.setAdapter(adapterListPet);

        return view;
    }

    private void getPets() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token
        int userId = sharedPreferences.getInt("id", 0);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<List<Pet>> call = interfaceServer.getPetsByUser(authToken, userId);

        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful()) {
                    for (Pet pet : response.body()) {
                        int id = pet.getId();
                        String name = pet.getName();
                        String nickname = pet.getNickname();
                        String type = pet.getType();
                        String img = pet.getImg();
                        String collarTag = pet.getCollar_tag();
                        int isOutside = pet.getIsOutside();
                        listPet.add(new Pet(id, name, nickname, type, img, collarTag, isOutside));
                        adapterListPet.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                // Afficher un message d'erreur
            }
        });
    }

    @Override
    public void clickManager(int position) {
        Log.d("PetsFragment", "clickManager: " + position);
    }
}