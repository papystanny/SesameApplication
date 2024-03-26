package com.example.sesameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.LoginResponse;
import unique.User;

public class ProfileFragment extends Fragment {

    EditText etEmail, etFirstName, etLastName, etPhone;
    Button btChangePassword, btChangeProfile;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        etEmail = view.findViewById(R.id.etEmailProfil);
        etFirstName = view.findViewById(R.id.etFirstNameProfil);
        etLastName = view.findViewById(R.id.etNameProfil);
        etPhone = view.findViewById(R.id.etPhoneProfil);

        btChangePassword = view.findViewById(R.id.btChangePassword);
        btChangeProfile = view.findViewById(R.id.btChangeProfile);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        etEmail.setText(sharedPreferences.getString("email", ""));
        etFirstName.setText(sharedPreferences.getString("firstname", ""));
        etLastName.setText(sharedPreferences.getString("lastname", ""));
        etPhone.setText(sharedPreferences.getString("phone", ""));

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.action_profileFragment_to_fragment_email_recup);
            }
        });

        btChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdate();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private boolean validateFirstName(String firstName) {
        if (firstName.length() < 2) {
            etFirstName.setError("Le prénom doit contenir plus de 2 caractères.");
            return false;
        }
        etFirstName.setError(null);
        return true;
    }

    private boolean validateLastName(String lastName) {
        if (lastName.length() < 2) {
            etLastName.setError("Le nom doit contenir plus de 2 caractères.");
            return false;
        }
        etLastName.setError(null);
        return true;
    }

    private boolean validateEmail(String email) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Format d'email invalide.");
            return false;
        }
        etEmail.setError(null);
        return true;
    }

    private boolean validatePhone(String phone) {
        String phonePattern = "\\d{3}-\\d{3}-\\d{4}";
        if (!phone.matches(phonePattern)) {
            etPhone.setError("Le téléphone doit être au format 111-111-1111.");
            return false;
        }
        etPhone.setError(null);
        return true;
    }

    private void attemptUpdate() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        boolean validFirstName = validateFirstName(firstName);
        boolean validLastName = validateLastName(lastName);
        boolean validEmail = validateEmail(email);
        boolean validPhone = validatePhone(phone);

        if (!validFirstName || !validLastName || !validEmail || !validPhone) {
            return;
        }

        updateProfile();
    }

    private void updateProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token;
        Toast.makeText(getContext(), "Token : " + token, Toast.LENGTH_SHORT).show();
        Log.d("Token", "Token : " + token);
        int id = sharedPreferences.getInt("id", -1);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<User> call = interfaceServer.updateUser(authToken, id, etFirstName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(getContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    Log.d("LoginResponse", "Statut HTTP : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Échec de la connexion: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LoginFailure", "Échec de la connexion", t);
            }
        });

        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.action_profileFragment_to_fragment_settings);
    }
}