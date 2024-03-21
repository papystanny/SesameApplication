package com.example.sesameapplication;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import unique.LockSchedule;

public class CreateScheduleFragment extends Fragment {

    View view;

    TextView tvLundi,tvMardi,tvMercredi,tvJeudi,tvVendredi,tvSamedi,tvDimanche, tvDotwError;
    EditText etStartTime, etEndTime;
    Button btAddScedule;
    boolean isLundiOrange,isMardiOrange,isMercrediOrange,isJeudiOrange,isVendrediOrange,isSamediOrange,isDimancheOrange = false;
    private List<String> daysSelected = new ArrayList<>(7);

    public CreateScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sortDaysSelected() {
        // Comparator to sort days of the week
        Comparator<String> dayComparator = new Comparator<String>() {
            @Override
            public int compare(String day1, String day2) {
                // Define the order of days
                String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                int index1 = -1;
                int index2 = -1;
                for (int i = 0; i < daysOfWeek.length; i++) {
                    if (day1.equals(daysOfWeek[i])) {
                        index1 = i;
                    }
                    if (day2.equals(daysOfWeek[i])) {
                        index2 = i;
                    }
                }
                return Integer.compare(index1, index2);
            }
        };

        // Sort the daysSelected list using the custom comparator
        Collections.sort(daysSelected, dayComparator);
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
        tvDotwError = view.findViewById(R.id.dotwError);

        etStartTime = view.findViewById(R.id.etStartTime);
        etEndTime = view.findViewById(R.id.etEndTime);

        btAddScedule = view.findViewById(R.id.btAddSchedule);

        resetButtonColors();

        tvDimanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isDimancheOrange){
                    tvDimanche.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isDimancheOrange = true;
                    daysSelected.add("Sunday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvDimanche.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isDimancheOrange = false;
                    daysSelected.remove("Sunday");
                }
            }
        });
        tvLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changer le backgroundTint à orange
                if(!isLundiOrange){
                    tvLundi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    isLundiOrange = true;
                    daysSelected.add("Monday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvLundi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isLundiOrange = false;
                    daysSelected.remove("Monday");
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
                    daysSelected.add("Tuesday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvMardi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isMardiOrange = false;
                    daysSelected.remove("Tuesday");
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
                    daysSelected.add("Wednesday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvMercredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isMercrediOrange = false;
                    daysSelected.remove("Wednesday");
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
                    daysSelected.add("Thursday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvJeudi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isJeudiOrange = false;
                    daysSelected.remove("Thursday");
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
                    daysSelected.add("Friday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvVendredi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isVendrediOrange = false;
                    daysSelected.remove("Friday");
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
                    daysSelected.add("Saturday");
                }
                // Changer le backgroundTint à gris
                else {
                    tvSamedi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                    isSamediOrange = false;
                    daysSelected.remove("Saturday");
                }
            }
        });

        btAddScedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateLockSchedule();
            }
        });

        return view;
    }

    private void createLockSchedule(String daysSelectedString, String startTime, String endTime, int recurring) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Pas de token trouvé");
        String authToken = "Bearer " + token;

        // Appeler la méthode pour ajouter un horaire
        for (String day : daysSelected) {
            InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
            Call<LockSchedule> call = interfaceServer.addLockSchedule(authToken, day, startTime, endTime, recurring);

            call.enqueue(new retrofit2.Callback<LockSchedule>() {
                @Override
                public void onResponse(Call<LockSchedule> call, retrofit2.Response<LockSchedule> response) {
                    if (response.isSuccessful()) {
                        // Afficher un message de succès
                        Log.d("Create Schedule Success", "LockSchedule created");
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                        navController.navigate(R.id.action_createScheduleFragment_to_lockScheduleFragment);
                    } else {
                        // Afficher un message d'erreur
                        Log.d("Create Schedule Error", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<LockSchedule> call, Throwable t) {
                    // Afficher un message d'erreur
                    Log.d("Create Schedule Failed", t.getMessage());
                }
            });
        }
    }

    private boolean validateDaysSelected() {
        if (daysSelected.isEmpty()) {
            tvDotwError.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            tvDotwError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateStartTime() {
        if (etStartTime.getText().toString().isEmpty()) {
            etStartTime.setError("Entrez une heure de déverrouillage");
            return false;
        }
        else if (!etStartTime.getText().toString().matches("((\\d|\\d{2}):\\d{2})")) {
            etStartTime.setError("Entrez une heure valide (HH:MM)");
            return false;
        }
        else {
            etStartTime.setError(null);
            return true;
        }
    }

    private boolean validateEndTime() {
        if (etEndTime.getText().toString().isEmpty()) {
            etEndTime.setError("Entrez une heure de verrouillage");
            return false;
        }
        else if (!etEndTime.getText().toString().matches("((\\d|\\d{2}):\\d{2})")) {
            etEndTime.setError("Entrez une heure valide (HH:MM)");
            return false;
        }
        else {
            etEndTime.setError(null);
            return true;
        }
    }

    private void attemptCreateLockSchedule() {
        sortDaysSelected();
        String daysSelectedString = daysSelected.toString();

        if (!validateDaysSelected() | !validateStartTime() | !validateEndTime()){
            return;
        }
        createLockSchedule(daysSelected.toString(), etStartTime.getText().toString(), etEndTime.getText().toString(), 1);
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
