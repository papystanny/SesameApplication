package com.example.sesameapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListActivity;
import others.AdapterListModifyPets;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;
import unique.PetActivity;

public class ListPetsFragment extends Fragment implements AdapterListModifyPets.InterfaceModifyPets {

    RecyclerView rvListPet;

    AdapterListModifyPets adapterListModifyPets;
    List<Pet> listPet = new ArrayList<>();

    public ListPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_pets, container, false);
        getPets();

        rvListPet = view.findViewById(R.id.rvListPet);


        rvListPet.setHasFixedSize(true);
        rvListPet.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapterListModifyPets = new AdapterListModifyPets(listPet, this, getActivity());
        rvListPet.setAdapter(adapterListModifyPets);

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
                    /*for (Pet pet : response.body()) {

                        String img = pet.getImg();

                        listPet.add(new Pet(img));

                        adapterListModifyPets.notifyDataSetChanged();
                    }*/
                    adapterListModifyPets.setList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                // Afficher un message d'erreur
                Toast.makeText(getContext(), "ERREUR", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void gestionClick(int position, Pet pet) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        Bundle bundle = new Bundle();
        bundle.putString("img", pet.getImg());
        bundle.putString("name", pet.getName());
        bundle.putString("nickname", pet.getNickname());

        navController.navigate(R.id.action_listPetsFragment_to_modifyPetsFragment, bundle);
    }

}
