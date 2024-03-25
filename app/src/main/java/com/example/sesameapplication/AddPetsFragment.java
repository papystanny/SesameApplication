package com.example.sesameapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;

public class AddPetsFragment extends Fragment {

    View view;
    String [] species = {"Chien", "Chat", "Lapin", "Poisson"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    ImageButton UploadBtn;
    AutoCompleteTextView auto_complete_txt;

    Button btCreatePet;
    private final int GALLERY_REQ_CODE = 1000;
    EditText etFirstName,etNickname;
    View dividerFirstName, dividerNickname, dividerSpecies;
    ActivityResultLauncher<String> pickphotoLauncher;
    ActivityResultLauncher<String[]> permissionsLauncher;

    public AddPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickphotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                    }
                });

        permissionsLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {

                        result.forEach((permission,reponse) ->
                        {
                            Log.d("PERMISSIONS", permission + " : " + reponse );
                        });
                    }
                }
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_pets, container, false);

        UploadBtn = view.findViewById(R.id.UploadBtn);
        etNickname = view.findViewById(R.id.etNickname);
        etFirstName = view.findViewById(R.id.etFirstName);
        auto_complete_txt = view.findViewById(R.id.auto_complete_txt);
        dividerFirstName = view.findViewById(R.id.dividerFirstName);
        dividerNickname = view.findViewById(R.id.dividerNickname);
        dividerSpecies = view.findViewById(R.id.dividerSpecies);

        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerFirstName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerFirstName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        etNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerNickname.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerNickname.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        auto_complete_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Le EditText a le focus, changer la couleur du textHint
                    dividerSpecies.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                } else {
                    // Le EditText n'a pas le focus, changer la couleur du textHint à sa couleur d'origine
                    dividerSpecies.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                }
            }
        });

        btCreatePet = view.findViewById(R.id.btCreatePet);

        autoCompleteTextView = view.findViewById((R.id.auto_complete_txt));
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_species_layout, species);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Espèces: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        btCreatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valide = true;
                TextView imageError = view.findViewById(R.id.imageError);


                if(etFirstName.getText().toString().trim().isEmpty())
                {
                    etFirstName.setError("Entrez le Prenom de votre compagnon");
                    valide = false;
                }
                if(etNickname.getText().toString().trim().isEmpty())
                {
                    etNickname.setError("Entrez le Surnom de votre compagnon");
                    valide = false;
                }
                if(auto_complete_txt.getText().toString().trim().isEmpty())
                {
                    auto_complete_txt.setError("Entrez l'espèce de votre compagnon");
                    valide = false;
                }
                if(UploadBtn.getContentDescription().toString().isEmpty())
                {
                    imageError.setVisibility(View.VISIBLE);
                    valide = false;
                }
                else
                {
                    imageError.setVisibility(View.GONE);

                }

                if(valide)
                {
                    addPet(etFirstName.getText().toString(), etNickname.getText().toString(),"", auto_complete_txt.getText().toString());
                }
            }
        });
        UploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifierPermission();
                Intent UploadBtn = new Intent(Intent.ACTION_PICK);
                UploadBtn.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(UploadBtn,GALLERY_REQ_CODE);
                //pickphotoLauncher.launch("image/*");
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE)
            {
                UploadBtn.setImageURI(data.getData());
            }

        }
    }

    public void verifierPermission()
    {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,};

        permissionsLauncher.launch(permissions);
    }



    private void addPet(String name, String nickName, String img, String type)
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token; // Formatage du token

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<Pet> call = interfaceServer.addPet(authToken, name, nickName, img, type); // Utilisation du token d'authentification

        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.fromAddPetToHomePage);
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to add pet", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(getContext(), "Opération échoue -404", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
