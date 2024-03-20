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

import others.AdapterListActivity;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;
import unique.PetActivity;

import java.util.ArrayList;
import java.util.List;


public class PetActivityFragment extends Fragment implements AdapterListActivity.InterfacePetActivity {

    View view;
    RecyclerView rvPetActivity;
    AdapterListActivity adapterList;
    public static List<PetActivity> listPetActivity = new ArrayList<>();
    public static List<Pet> listPet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pet_activity, container, false);

        if (listPetActivity.size() > 0) {
            listPetActivity.clear();
        }
        getActivities();

        rvPetActivity = view.findViewById(R.id.rvPetActivity);
        rvPetActivity.setHasFixedSize(true);
        rvPetActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterList = new AdapterListActivity(listPetActivity, this, listPet);
        rvPetActivity.setAdapter(adapterList);

        return view;
    }

    private void getActivities() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token;
        int userId = sharedPreferences.getInt("id", -1);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<List<PetActivity>> call = interfaceServer.getPetActivity(authToken, userId);

        call.enqueue(new Callback<List<PetActivity>>() {
            @Override
            public void onResponse(Call<List<PetActivity>> call, Response<List<PetActivity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (PetActivity petActivity : response.body()) {
                        int inOrOut = petActivity.isInOrOut();
                        String created_at = petActivity.getCreated_at();
                        String collar_tag = petActivity.getCollar_tag();
                        listPetActivity.add(new PetActivity(inOrOut, created_at, collar_tag));
                        adapterList.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(getContext(), "BAD RESPONSE : " + response.errorBody(), Toast.LENGTH_LONG).show();
                    Log.d("BAD RESPONSE", "BAD RESPONSE : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<PetActivity>> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Pet>> call2 = interfaceServer.getPetsByUser(authToken, userId);

        call2.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Pet pet : response.body()) {
                        int id = pet.getId();
                        String name = pet.getName();
                        String nickname = pet.getNickname();
                        String type = pet.getType();
                        String img = pet.getImg();
                        String collarTag = pet.getCollar_tag();
                        int isOutside = pet.getIsOutside();
                        listPet.add(new Pet(id, name, nickname, type, img, collarTag, isOutside));
                        adapterList.notifyDataSetChanged();
                    }
                    Log.d("Pets", "SUCCESS : " + listPet);
                } else {
                    Toast.makeText(getContext(), "BAD RESPONSE : Erreur lors de la récupération des animaux", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void clickManager(int position, PetActivity petActivity) {

    }
}
