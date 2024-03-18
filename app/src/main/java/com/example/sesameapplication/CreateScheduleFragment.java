package com.example.sesameapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CreateScheduleFragment extends Fragment {

    View view;

    TextView tvLundi,tvMardi,tvMercredi,tvJeudi,tvVendredi,tvSamedi,tvDimanche;

    Button btAddScedule;
    boolean isLundiOrange,isMardiOrange,isMercrediOrange,isJeudiOrange,isVendrediOrange,isSamediOrange,isDimancheOrange = false;

    public CreateScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_schedule, container, false);

        tvLundi = view.findViewById(R.id.tvLundi);
        tvMardi = view.findViewById(R.id.tvMardi);
        tvMercredi = view.findViewById(R.id.tvMercredi);
        tvJeudi = view.findViewById(R.id.tvJeudi);
        tvVendredi = view.findViewById(R.id.tvVendredi);
        tvSamedi = view.findViewById(R.id.tvSamedi);
        tvDimanche = view.findViewById(R.id.tvDimanche);

        btAddScedule = view.findViewById(R.id.btAddScedule);


        tvLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isLundiOrange){
                    tvLundi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isLundiOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvLundi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isLundiOrange = false;
                }
            }
        });
        tvMardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isMardiOrange){
                    tvMardi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isMardiOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvMardi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isMardiOrange = false;
                }
            }
        });
        tvMercredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isMercrediOrange){
                    tvMercredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isMercrediOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvMercredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isMercrediOrange = false;
                }
            }
        });
        tvJeudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isJeudiOrange){
                    tvJeudi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isJeudiOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvJeudi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isJeudiOrange = false;
                }
            }
        });
        tvVendredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isVendrediOrange){
                    tvVendredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isVendrediOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvVendredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isVendrediOrange = false;
                }
            }
        });
        tvSamedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isSamediOrange){
                    tvSamedi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isSamediOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvSamedi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isSamediOrange = false;
                }
            }
        });
        tvDimanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isDimancheOrange){
                    tvDimanche.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isDimancheOrange = true;
                }
                // Changer le backgroundTint à gris
                else {
                    tvDimanche.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isDimancheOrange = false;
                }
            }
        });

        btAddScedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtonColors();
            }
        });


        return view;
    }

    private void resetButtonColors() {
        tvLundi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvMardi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvMercredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvJeudi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvVendredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvSamedi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
        tvDimanche.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
    }
}