package com.example.sesameapplication;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import others.AdapterListHomePage;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;


public class HomeFragment extends Fragment implements AdapterListHomePage.InterfacePet {

    View view;
    RecyclerView rvHomePage;
    ImageView ibHomeLock, ibSchedule;
    TextView tvMsg;
    AdapterListHomePage.InterfacePet interfacePet;
    AdapterListHomePage adapterListHomePage;
    String isLocked = "Porte verrouillée";
    private List<Pet> listPet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (listPet.size() > 0) {
            listPet.clear();
        }
        getPets();
        rvHomePage = view.findViewById(R.id.rvHomePage);
        rvHomePage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHomePage.setHasFixedSize(true);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(rvHomePage);
        adapterListHomePage = new AdapterListHomePage(listPet, this);
        rvHomePage.setAdapter(adapterListHomePage);

        // Appeler la méthode pour afficher les SharedPreferences
        displaySharedPreferences();

        // Récupération des éléments de la vue
        ibHomeLock = view.findViewById(R.id.ibHomeLock);
        ibSchedule = view.findViewById(R.id.ibSchedule);
        tvMsg = view.findViewById(R.id.tvMsg);
        getStatusDoor();

        ibHomeLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockAndUnlock();
            }
        });

        ibSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.action_fragment_home_to_lockScheduleFragment );
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
                    for (Pet pet : response.body()) {
                        int id = pet.getId();
                        String name = pet.getName();
                        String nickname = pet.getNickname();
                        String type = pet.getType();
                        String img = pet.getImg();
                        String collarTag = pet.getCollar_tag();
                        int isOutside = pet.getIsOutside();
                        listPet.add(new Pet(id, name, nickname, type, img, collarTag, isOutside));
                        adapterListHomePage.notifyDataSetChanged();
                    }
                    Log.d("Pets", "SUCCESS : " + listPet);
                } else {
                    Toast.makeText(getContext(), "BAD RESPONSE : Erreur lors de la récupération des animaux", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : " + t + "-404", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStatusDoor(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token
        int id = sharedPreferences.getInt("id", -1);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = interfaceServer.getStatusDoor(authToken);

        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String texte = response.body().getMessage().toString();
                    tvMsg.setText(texte);
                    if (isLocked.equals(texte)) {
                        ibHomeLock.setImageResource(R.drawable.close);
                        isLocked =texte;

                    } else {
                        ibHomeLock.setImageResource(R.drawable.open);
                        isLocked =texte;
                    }
                } else {
                    Toast.makeText(getContext(), "BAD RESPONSE Lors de la récupération du statut de la porte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : " + t + "-404", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void lockAndUnlock(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token
        int id = sharedPreferences.getInt("id", -1);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = interfaceServer.lockAndUnlock(authToken);

        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.action_fragment_home_self );
                    //Toast.makeText(getContext(), "Changement réussi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "BAD RESPONSE Lors de la récupération du statut de la porte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "FAILURE : " + t + "-404", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void gestionClick(int position, Pet pet) {
        //Toast.makeText(getContext(), "Click on " + pet.getName(), Toast.LENGTH_SHORT).show();
    }
}
