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

        //checkLogin();

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

            Toast.makeText(getContext(), "Connexion automatique réussie", Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!isValidEmail(email)) {
            Toast.makeText(getContext(), "Email invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 2) {
            Toast.makeText(getContext(), "Le mot de passe doit contenir plus de 2 caractères", Toast.LENGTH_SHORT).show();
            return;
        }

        // Faire l'appel API ici
        login(email, password);
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

