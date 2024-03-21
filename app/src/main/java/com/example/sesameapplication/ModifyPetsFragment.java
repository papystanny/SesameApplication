package com.example.sesameapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;

public class ModifyPetsFragment extends Fragment {

    View view;

    ImageButton UploadBtn;
    ActivityResultLauncher<PickVisualMediaRequest> picker;

    ActivityResultLauncher<Intent> photoLauncher;

    AutoCompleteTextView auto_complete_txt;

    File fichier;

    Button btModifyPet;
    Uri imageUri;
    //private final int GALLERY_REQ_CODE = 1000;
    EditText etFirstName,etNickname;

    ActivityResultLauncher<String> pickphotoLauncher;
    ActivityResultLauncher<String[]> permissionsLauncher;

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

        picker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {


                        if(o != null)
                        {
                            UploadBtn.setImageURI(o);
                            String path = null;
                            String[] projection = { MediaStore.Images.Media.DATA };
                            Cursor cursor = getActivity().getContentResolver().query(imageUri, projection, null, null, null);
                            if (cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                path = cursor.getString(columnIndex);
                            }
                            cursor.close();
                            fichier = new File(path);
                        }



                    }
                });


        photoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                        /*Bundle bundle = o.getData().getExtras();
                        Bitmap bitmap = (Bitmap)bundle.get("data");
                        ivImage.setImageBitmap(bitmap);
                        try {
                            sauvegarderPhoto(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }*/
                        if(o.getResultCode() == RESULT_OK)
                        {
                            UploadBtn.setImageURI(imageUri);

                            String path = null;
                            String[] projection = { MediaStore.Images.Media.DATA };
                            Cursor cursor = getActivity().getContentResolver().query(imageUri, projection, null, null, null);
                            if (cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                path = cursor.getString(columnIndex);
                            }
                            cursor.close();
                            fichier = new File(path);
                        }



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
        view = inflater.inflate(R.layout.fragment_modify_pets, container, false);

        UploadBtn = view.findViewById(R.id.UploadBtn);
        etNickname = view.findViewById(R.id.etNickname);
        etFirstName = view.findViewById(R.id.etFirstName);
        auto_complete_txt = view.findViewById(R.id.auto_complete_txt);

        btModifyPet = view.findViewById(R.id.btCreatePet);

        btModifyPet.setOnClickListener(new View.OnClickListener() {
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
                /*if(auto_complete_txt.getText().toString().trim().isEmpty())
                {
                    auto_complete_txt.setError("Entrez l'espèce de votre compagnon");
                    valide = false;
                }*/
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
                    modifyPet(etFirstName.getText().toString(), etNickname.getText().toString(),"");
                }
            }
        });
        UploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifierPermission();
                //  Intent UploadBtn = new Intent(Intent.ACTION_PICK);
                //   UploadBtn.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //   startActivityForResult(UploadBtn,GALLERY_REQ_CODE);
                //pickphotoLauncher.launch("image/*");


                /*picker.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());*/
                takePicture();
            }
        });



        return view;
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

        RequestBody nom = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody surnom = RequestBody.create(MediaType.parse("text/plain"), nickName);
        RequestBody espece = RequestBody.create(MediaType.parse("text/plain"), type);

        MediaType mediaType = MediaType.parse("image/*");
        RequestBody fichier_requete = RequestBody.create(mediaType,fichier);

        MultipartBody.Part part_fichier = MultipartBody.Part.createFormData("fichier",
                fichier.getName(),
                fichier_requete);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<SimpleApiResponse> call = interfaceServer.addPet(authToken, nom, surnom, part_fichier, espece); // Utilisation du token d'authentification

        call.enqueue(new Callback<SimpleApiResponse>() {
            @Override
            public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                if (response.isSuccessful()) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                    //navController.navigate(R.id.);
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to add pet", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Opération échoue -404", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void takePicture()
    {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri ;
            ContentResolver resolver = getActivity().getContentResolver();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            else
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + "_IMG.jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");

            imageUri = resolver.insert(uri, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            photoLauncher.launch(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}