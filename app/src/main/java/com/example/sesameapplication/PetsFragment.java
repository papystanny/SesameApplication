package com.example.sesameapplication;

import static com.example.sesameapplication.PetActivityFragment.listPetActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListPet;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;
import unique.PetActivity;


public class PetsFragment extends Fragment implements AdapterListPet.InterfacePet {

    ImageButton ibAddPet;
    RecyclerView rvPets;
    TextView tvNoPetYet;
    AdapterListPet adapterListPet;
    List<Pet> listPet = new ArrayList<>();
    List<PetActivity> listPetActivity = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pets, container, false);

        ibAddPet = view.findViewById(R.id.ibAddPet);
        tvNoPetYet = view.findViewById(R.id.tvNoPetYet);

        ibAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.action_petsFragment3_to_addPetsFragment);
            }
        });

        if (listPet.size() > 0) {
            listPet.clear();
        }
        getPets();
        getPetActivity();
        rvPets = view.findViewById(R.id.rvPets);
        rvPets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPets.setHasFixedSize(true);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(rvPets);
        adapterListPet = new AdapterListPet(listPet, this, listPetActivity);
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
                        if (!listPet.isEmpty()){
                            rvPets.setVisibility(View.VISIBLE);
                            tvNoPetYet.setVisibility(View.GONE);
                        } else {
                            rvPets.setVisibility(View.GONE);
                            tvNoPetYet.setVisibility(View.VISIBLE);
                        }
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

    private void getPetActivity() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token
        int userId = sharedPreferences.getInt("id", 0);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<List<PetActivity>> call = interfaceServer.getPetActivity(authToken, userId);

        call.enqueue(new Callback<List<PetActivity>>() {
            @Override
            public void onResponse(Call<List<PetActivity>> call, Response<List<PetActivity>> response) {
                if (response.isSuccessful()) {
                    for (PetActivity petActivity : response.body()) {
                        int inOrOut = petActivity.isInOrOut();
                        String created_at = petActivity.getCreated_at();
                        String collar_tag = petActivity.getCollar_tag();
                        listPetActivity.add(new PetActivity(inOrOut, created_at, collar_tag));
                        adapterListPet.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PetActivity>> call, Throwable t) {
                // Afficher un message d'erreur
            }
        });
    }

    @Override
    public void clickManager(int position) {
        Log.d("PetsFragment", "clickManager: " + position);
    }
}