package com.example.sesameapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import others.AdapterListActivity;
import unique.PetActivity;

import java.util.ArrayList;
import java.util.List;


public class PetActivityFragment extends Fragment implements AdapterListActivity.InterfacePetActivity {

    View view;
    RecyclerView rvPetActivity;
    public static List<PetActivity> listPetActivity = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pet_activity, container, false);

        listPetActivity.add(new PetActivity(true, "12h34", "Aujourd'hui", "ic_dog"));
        listPetActivity.add(new PetActivity(false, "17h29", "Hier", "ic_dog"));
        listPetActivity.add(new PetActivity(true, "10h16", "Hier", "ic_dog"));
        listPetActivity.add(new PetActivity(false, "8h44", "Il y a 2 jours", "ic_dog"));
        listPetActivity.add(new PetActivity(true, "9h33", "Il y a 3 jours", "ic_dog"));
        listPetActivity.add(new PetActivity(true, "9h33", "Il y a 3 jours", "ic_dog"));
        Log.d("ListPetActivity", listPetActivity.toString());

        rvPetActivity = view.findViewById(R.id.rvPetActivity);


        rvPetActivity.setHasFixedSize(true);
        rvPetActivity.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterListActivity adapterList = new AdapterListActivity(listPetActivity, this);
        rvPetActivity.setAdapter(adapterList);
        // Inflate the layout for this fragment

        return view;
    }


    @Override
    public void clickManager(int position, PetActivity petActivity) {

    }
}
