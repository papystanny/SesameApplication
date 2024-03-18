package com.example.sesameapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import others.AdapterListSchedule;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.LockSchedule;

public class LockScheduleFragment extends Fragment implements AdapterListSchedule.InterfaceSchedule {
    ImageButton ibAddSchedule;
    RecyclerView rvSchedule;
    AdapterListSchedule adapterListSchedule;
    List<LockSchedule> listSchedule = new ArrayList<>();
    public LockScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lock_schedule, container, false);

        if (listSchedule.size() > 0) {
            listSchedule.clear();
        }
        getLockSchedules();
        rvSchedule = view.findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListSchedule = new AdapterListSchedule(listSchedule, this);
        rvSchedule.setAdapter(adapterListSchedule);

        ibAddSchedule = view.findViewById(R.id.ibAddSchedule);
        ibAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.action_lockScheduleFragment_to_createScheduleFragment);
            }
        });

        return view;
    }

    private void getLockSchedules() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String authToken = "Bearer " + token;
        int id = sharedPreferences.getInt("id", 0);

        InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
        Call<List<LockSchedule>> call = interfaceServer.getLockSchedules(authToken, id);

        call.enqueue(new Callback<List<LockSchedule>>() {
            @Override
            public void onResponse(Call<List<LockSchedule>> call, Response<List<LockSchedule>> response) {
                if (response.isSuccessful()) {
                    for (LockSchedule lockSchedule : response.body()) {
                        String dayOfWeek = lockSchedule.getDayOfWeek();
                        String openTime = lockSchedule.getOpenTime();
                        String closeTime = lockSchedule.getCloseTime();
                        int recurring = lockSchedule.getRecurring();
                        listSchedule.add(new LockSchedule(dayOfWeek, openTime, closeTime, recurring));
                        adapterListSchedule.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LockSchedule>> call, Throwable t) {
                Log.d("LockSchedule", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void clickManager(int position) {
        Log.d("LockSchedule", "clickManager: " + position);
    }
}