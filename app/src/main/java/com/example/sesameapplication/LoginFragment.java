package com.example.sesameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.LoginResponse;
import unique.User;


public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btLogin ;
    private TextView btRegister, btPasswordForget ;
    private View dividerEmail, dividerPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkLogin();

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etMdp);
        btLogin = view.findViewById(R.id.btLogin);
        dividerEmail = view.findViewById(R.id.dividerEmail);
        dividerPassword = view.findViewById(R.id.dividerMdp);
        btRegister = view.findViewById(R.id.tvSignUpLink);
        btPasswordForget = view.findViewById(R.id.tvMdpForgot);

        Toast.makeText(getContext(), "J'entre", Toast.LENGTH_SHORT).show();

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerEmail.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerEmail.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerPassword.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerPassword.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                attemptLogin();

            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.fromLoginToRegister);
            }
        });

        btPasswordForget.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.fromHomeToEmailRecup);
            }
        });
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Vérifiez si le token existe déjà
        if (token != null) {

            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            navController.navigate(R.id.fromLoginToHome);
        }
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Valider l'email
        if (!validateEmail(email)) {
            // Si l'email est invalide, validateEmail aura déjà affiché le message d'erreur
            return;
        }

        // Valider le mot de passe
        if (!validatePassword(password)) {
            // Si le mot de passe est invalide, validatePassword aura déjà affiché le message d'erreur
            return;
        }

        // Si les validations passent, faire l'appel API
        login(email, password);
    }

    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        if (!password.matches(passwordPattern)) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères, dont un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.");
            return false;
        }
        etPassword.setError(null); // Clear previous error
        return true;
    }

    private boolean validateEmail(String email) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Format d'email invalide.");
            return false;
        }
        etEmail.setError(null); // Clear previous error
        return true;
    }


    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void login(String email, String password) {
        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<LoginResponse> call = interfaceServer.login(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

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

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromLoginToHome);


                    Toast.makeText(getContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    Log.d("LoginResponse", "Statut HTTP : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Échec de la connexion: " + t.getMessage(), Toast.LENGTH_LONG).show();

                Log.e("LoginFailure", "Échec de la connexion", t);
            }

        });
    }
}

