package com.example.sesameapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reseau_api.InterfaceServer;

public class CodeRecupFragment extends Fragment {

    private EditText etCode;
    private Button btVerify;
    private String userEmail;
    private View dividerEmail;
    private String testE ="test";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_recup, container, false);
        etCode = view.findViewById(R.id.etEmail); // Assurez-vous que cet ID correspond à votre EditText pour le code
        btVerify = view.findViewById(R.id.btLogin); // Assurez-vous que cet ID correspond à votre Button
        dividerEmail = view.findViewById(R.id.dividerEmail);

        if (getArguments() != null && getArguments().containsKey("userEmail")) {
            userEmail = getArguments().getString("userEmail");
        }

        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = etCode.getText().toString().trim();
                if (isValidCode(token)) {
                    verifyResetToken(userEmail, token);
                } else {
                    etCode.setError("Le code doit contenir 7 chiffres.");
                }
            }
        });

        return view;
    }

    private boolean isValidCode(String code) {
        return !TextUtils.isEmpty(code) && TextUtils.isDigitsOnly(code) && code.length() == 7;
    }

    private void verifyResetToken(String email, String token) {
        InterfaceServer apiService =  RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = apiService.verify_Reset_Token(email, token);
        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     Bundle bundle = new Bundle();
                    Toast.makeText(getContext(), userEmail,  Toast.LENGTH_SHORT).show();
                     bundle.putString("email",userEmail);
                    if (getArguments() != null && getArguments().containsKey("email")) {
                        testE = getArguments().getString("email");
                    }
                    else{
                        Toast.makeText(getContext(), "la faute est ici", Toast.LENGTH_SHORT).show();
                    }
                //    Log.e("code_recup", testE);
                    // Toast.makeText(getContext(), testE, Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromEmailRecupCodeToPasswordRecup,bundle);
                } else {
                    Toast.makeText(getContext(), "Code invalide ou expiré", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Échec de la connexion: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("code_recup", "echec de connexion ", t);
            }
        });
    }
}
