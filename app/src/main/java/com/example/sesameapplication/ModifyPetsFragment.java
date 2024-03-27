package com.example.sesameapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;

public class ModifyPetsFragment extends Fragment {

    private View view;
    private ImageButton uploadBtn;
    private Button btModifyPet;
    private EditText etFirstName, etNickname;
    private Uri selectedImageUri = null;
    private String initialImageUri = null;

    int petId;
    private ActivityResultLauncher<String> pickPhotoLauncher;
    private ActivityResultLauncher<String[]> permissionsLauncher;

    public ModifyPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setupLaunchers();
    }

    private void setupLaunchers() {
        pickPhotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                uploadBtn.setImageURI(result);
                selectedImageUri = result;
            }
        });

        permissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean allPermissionsGranted = result.values().stream().allMatch(granted -> granted);
            if (allPermissionsGranted) {
                pickPhotoLauncher.launch("image/*");
            } else {
                pickPhotoLauncher.launch("image/*");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modify_pets, container, false);
        initializeViews();
        loadPetDetails();
        setupClickListeners();
        return view;
    }

    private void initializeViews() {
        uploadBtn = view.findViewById(R.id.UploadBtn);
        etNickname = view.findViewById(R.id.etNickname);
        etFirstName = view.findViewById(R.id.etFirstName);
        btModifyPet = view.findViewById(R.id.btModifyPet);
    }

    private void loadPetDetails() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String imgPet = bundle.getString("img");
            initialImageUri = imgPet;
            Picasso.get().load(imgPet).into(uploadBtn);

            String namePet = bundle.getString("name");
            etFirstName.setText(namePet);

            String nicknamePet = bundle.getString("nickname");
            etNickname.setText(nicknamePet);

             petId = bundle.getInt("id");
        }
    }

    private void setupClickListeners() {
        btModifyPet.setOnClickListener(v -> attemptModifyPet());
        uploadBtn.setOnClickListener(v -> requestPermissionsAndPickPhoto());
    }

    private void requestPermissionsAndPickPhoto() {
        permissionsLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void attemptModifyPet() {
        if (validateInputs()) {
            // Utilise l'image sélectionnée s'il y en a une, sinon utilise l'image initiale.
            String imageUri = selectedImageUri != null ? selectedImageUri.toString() : initialImageUri;
            modifyPet(etFirstName.getText().toString(), petId, etNickname.getText().toString(), imageUri);
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;
        TextView imageError = view.findViewById(R.id.imageError);

        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError("Enter your companion's first name");
            isValid = false;
        }
        if (etNickname.getText().toString().trim().isEmpty()) {
            etNickname.setError("Enter your companion's nickname");
            isValid = false;
        }

        // Assuming image validation is not needed as you are avoiding permission requests

        return isValid;
    }

    private void modifyPet(String name, int petId, String nickName, String imgUri) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token;

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<Pet> call = interfaceServer.modifyPet(authToken, petId, name, nickName, imgUri);

        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    navigateToHome();
                    Toast.makeText(getContext(), "Pet modified successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to modify pet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                // Log de l'URL complète de la requête
                Log.e("AddPetError", "Failed request URL: " + call.request().url().toString());
                // Afficher le message d'erreur dans un Toast
                Toast.makeText(getContext(), "Operation failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void navigateToHome() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.action_modifyPetsFragment_to_fragment_home);
    }
}
