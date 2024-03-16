package com.example.sesameapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Map;

public class AddPetsFragment extends Fragment {

    View view;
    String [] species = {"Chien", "Chat", "Lapin", "Poisson"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    ImageButton UploadBtn;

    private final int GALLERY_REQ_CODE = 1000;

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

        autoCompleteTextView = view.findViewById((R.id.auto_complete_txt));
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_species, species);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Espèces: " + item, Toast.LENGTH_SHORT).show();
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
}