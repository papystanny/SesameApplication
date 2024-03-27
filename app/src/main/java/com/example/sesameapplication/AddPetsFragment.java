package com.example.sesameapplication;

import static android.app.Activity.RESULT_OK;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;
import unique.FilePathUtil;

public class AddPetsFragment extends Fragment {

    String[] species = {"Chien", "Chat", "Lapin", "Poisson"};
    View view;
    ImageButton UploadBtn;
    private Uri selectedImageUri = null;
    Button btCreatePet;
    AutoCompleteTextView autoCompleteTextView;
    EditText etFirstName, etNickname;
    View dividerFirstName, dividerNickname, dividerSpecies;
    ActivityResultLauncher<String> pickPhotoLauncher;
    ActivityResultLauncher<String[]> permissionsLauncher;

    public AddPetsFragment() {
        //
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
        pickPhotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onPickPhotoResult);
        permissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), this::onPermissionResult);
    }

    private void onPickPhotoResult(Uri uri) {
        if (uri != null) {
            UploadBtn.setImageURI(uri);
            selectedImageUri = uri;
        }
    }

    private void onPermissionResult(Map<String, Boolean> permissions) {
        boolean granted = permissions.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);
        if (granted) {
            openGallery();
        } else {
            openGallery();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_pets, container, false);
        initializeViews();
        return view;
    }

    private void initializeViews() {
        UploadBtn = view.findViewById(R.id.UploadBtn);
        btCreatePet = view.findViewById(R.id.btCreatePet);
        etFirstName = view.findViewById(R.id.etFirstName);
        etNickname = view.findViewById(R.id.etNickname);
        dividerFirstName = view.findViewById(R.id.dividerFirstName);
        dividerNickname = view.findViewById(R.id.dividerNickname);
        dividerSpecies = view.findViewById(R.id.dividerSpecies);
        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, species);
        autoCompleteTextView.setAdapter(adapter);

        btCreatePet.setOnClickListener(v -> attemptAddPet());
        UploadBtn.setOnClickListener(v -> requestPermissionsAndPickPhoto());
    }

    private void attemptAddPet() {
        String petType = autoCompleteTextView.getText().toString(); // Utilise la valeur sélectionnée comme type de l'animal
        if (validateInputs()) {
            addPet(etFirstName.getText().toString(), etNickname.getText().toString(), selectedImageUri, petType);
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError("Entrez de le nom du compagnon");
            isValid = false;
        }

        if (etNickname.getText().toString().trim().isEmpty()) {
            etNickname.setError("Entrez le surnom du compagnon");
            isValid = false;
        }

        if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
            autoCompleteTextView.setError("Entrez le type du compagnon");
            isValid = false;
        }

        TextView imageError = view.findViewById(R.id.imageError);
        if (selectedImageUri == null) {
            if(imageError != null) imageError.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Sélectionner une image pour le pet", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            if(imageError != null) imageError.setVisibility(View.GONE);
        }

        return isValid;
    }


    private void addPet(String name, String nickName, Uri imageUri, String type) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token;

        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody nickNamePart = RequestBody.create(MediaType.parse("text/plain"), nickName);
        RequestBody typePart = RequestBody.create(MediaType.parse("text/plain"), type);

        MultipartBody.Part imagePart = null;
        if (imageUri != null) {
            String realPath = FilePathUtil.getPath(getContext(), imageUri);
            if (realPath != null) {
                File file = new File(realPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                imagePart = MultipartBody.Part.createFormData("fichier", file.getName(), requestFile);
            } else {
                Toast.makeText(getContext(), "Impossible d'avoir l'image", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<Pet> call = interfaceServer.addPet(authToken, namePart, nickNamePart, typePart, imagePart);

        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.action_addPetsFragment_to_fragment_home);
                    Toast.makeText(getContext(), "Pet added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to add pet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Log.e("AddPetError", "Request failed: " + call.request().url().toString() + ", Error: " + t.getMessage());
                Toast.makeText(getContext(), "Operation failed -404. Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void requestPermissionsAndPickPhoto() {
        permissionsLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhotoLauncher.launch("image/*");
    }
}
