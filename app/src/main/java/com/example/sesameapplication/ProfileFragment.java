package com.example.sesameapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

    EditText etEmail, etFirstName, etLastName, etPhone;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        etEmail = view.findViewById(R.id.etEmailProfil);
        etFirstName = view.findViewById(R.id.etFirstNameProfil);
        etLastName = view.findViewById(R.id.etNameProfil);
        etPhone = view.findViewById(R.id.etPhoneProfil);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        etEmail.setText(sharedPreferences.getString("email", ""));
        etFirstName.setText(sharedPreferences.getString("firstname", ""));
        etLastName.setText(sharedPreferences.getString("lastname", ""));
        etPhone.setText(sharedPreferences.getString("phone", ""));

        // Inflate the layout for this fragment
        return view;
    }
}