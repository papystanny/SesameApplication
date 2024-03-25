package com.example.sesameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {

    private Button logoutButton, bMyPetsSetting;

    String [] language = {"Francais", "English"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the logout button
        logoutButton = view.findViewById(R.id.bLogoutSetting);
        bMyPetsSetting = view.findViewById(R.id.bMyPetsSetting);

        autoCompleteTextView = view.findViewById((R.id.auto_complete_txt));
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_languages_layout, language);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Langue: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        bMyPetsSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.fromSettingsToListPets);
            }
        });

        return view;
    }

    private void logoutUser() {
        //
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token


        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = interfaceServer.logout(authToken); // Utilisation du token d'authentification

        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful()) {
                    //
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromSettingsToLogin);
                } else {
                    Toast.makeText(getContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Opération échoue -404", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
