package com.example.sesameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.LoginResponse;
import unique.User;


public class CreateAccountFragment extends Fragment {
    private EditText etFirstName, etLastName, etEmail, etPhone, etPassword;
    private Button btRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etMdp);
        btRegister = view.findViewById(R.id.btRegister);

        btRegister.setOnClickListener(v -> attemptRegister());
        return view;
    }

    private void attemptRegister() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!validateFirstName(firstName) | !validateLastName(lastName) |
                !validateEmail(email) | !validatePhone(phone) | !validatePassword(password)) {
            // Si une validation échoue, un message d'erreur a déjà été affiché par les méthodes de validation.
            return;
        }

        // Tout est valide, procéder à l'inscription
        register(firstName, lastName, phone, email, password);
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

    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        if (!password.matches(passwordPattern)) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères, dont un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.");
            return false;
        }
        etPassword.setError(null);
        return true;
    }

    private void register(String firstName, String lastName, String phone, String email, String password) {
        InterfaceServer service = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<LoginResponse> call = service.register(firstName, lastName, phone, email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Stockage du token et des infos utilisateur
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("token", response.body().getToken());

                    User user = response.body().getUser();
                    editor.putInt("id", user.getId());
                    editor.putString("firstname", user.getFirstname());
                    editor.putString("lastname", user.getLastname());
                    editor.putString("phone", user.getPhone());
                    editor.putString("email", user.getEmail());

                    editor.apply();

                    Toast.makeText(getContext(), "Compte créé avec succès", Toast.LENGTH_LONG).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromRegisterToHome);
                } else {
                    // Gestion des erreurs
                    String errorMessage = "Échec de création de compte"; // Message par défaut
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        errorMessage = jsonObject.getString("message");
                    } catch (Exception e) {
                        Log.e("API Error", "Erreur lors du parsing du message d'erreur", e);
                    }
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Gestion de l'échec de la demande
                Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }
}


