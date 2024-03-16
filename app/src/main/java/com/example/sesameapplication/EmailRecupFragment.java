package com.example.sesameapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import unique.User;


public class EmailRecupFragment extends Fragment {

    private EditText etEmail;
    View dividerEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_recup, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        dividerEmail = view.findViewById(R.id.dividerEmail);
        Button btSendResetCode = view.findViewById(R.id.btLogin);

        //g1ng3r
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

        btSendResetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                if (!isValidEmail(email)) {
                    etEmail.setError("Format de courriel invalide.");
                    return;
                }
                sendResetCode(email);
            }
        });

        return view;
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void sendResetCode(String email) {
        InterfaceServer apiService =  RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<User> call = apiService.send_reset_code(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    showSuccessDialog();
                } else {
                    Toast.makeText(getContext(), "L'email n'est pas associé à un compte.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de réseau", Toast.LENGTH_SHORT).show();
                Log.e("fragment_email_recup", "Erreur lors de l'appel API", t);
            }
        });
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Un code de réinitialisation a été envoyé, il sera valide pendant 10 minutes. Veuillez vérifier votre courriel." )
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", etEmail.getText().toString());
                        navController.navigate(R.id.fromEmailRecupToEmailRecupCode, bundle);
                    }
                })
                .show();
    }
}
