package com.example.sesameapplication;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PasswordRecupFragment extends Fragment {

    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btChangePassword;
    private String userEmail;
    View dividerMdpConfirm;
    View dividerMdp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_recup, container, false);

        etPassword = view.findViewById(R.id.etMdp);
        etPasswordConfirm = view.findViewById(R.id.etMdpConfirm);
        btChangePassword = view.findViewById(R.id.btLogin);
        dividerMdpConfirm = view.findViewById(R.id.dividerMdpConfrim);
        dividerMdp = view.findViewById(R.id.dividerMdp);

        if (getArguments() != null && getArguments().containsKey("email")) {
            userEmail = getArguments().getString("email");
        }
        else{
            Toast.makeText(getContext(), "la faute est ici", Toast.LENGTH_SHORT).show();
        }

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerMdp.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerMdp.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        etPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerMdpConfirm.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerMdpConfirm.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String passwordConfirm = etPasswordConfirm.getText().toString().trim();

                if (!validatePassword(password)) {
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    etPasswordConfirm.setError("Les mots de passe ne correspondent pas.");
                    return;
                }
                Toast.makeText(getContext(), "sds" + userEmail, Toast.LENGTH_SHORT).show();
                changePassword(userEmail, password, passwordConfirm);
            }
        });

        return view;
    }

    private boolean validatePassword( String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        if (!password.matches(passwordPattern)) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères, dont un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.");
            return false;
        }
        etPassword.setError(null);
        return true;
    }

    private void changePassword(String email, String password, String passwordConfirm) {
        InterfaceServer apiService = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = apiService.changePassword(email,password, passwordConfirm);
        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromPasswordRecupToHome);
                } else {
                    Toast.makeText(getContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Échec de la connexion: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
