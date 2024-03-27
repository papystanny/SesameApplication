package others;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.Pet;

public class AdapterListModifyPets extends RecyclerView.Adapter<AdapterListModifyPets.ViewHolder>{

    public interface InterfaceModifyPets{
        public void gestionClick(int position, Pet pet);
    }

    InterfaceModifyPets interfaceModifyPets;
    List<Pet> list;
    Activity activity;
    Bundle bundle;

    public AdapterListModifyPets(List<Pet> list, InterfaceModifyPets interfaceModifyPets, Activity activity, Bundle bundle)
    {
        this.list = list;
        this.interfaceModifyPets = interfaceModifyPets;
        this.activity = activity;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public AdapterListModifyPets.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pet_settings_layout,parent,false);
        return new AdapterListModifyPets.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Pet pet = list.get(position);
        String name = pet.getName();
        String image = pet.getImg();
        vh.tvName.setText(list.get(position).getName());
        Picasso.get().load(list.get(position).getImg()).into(vh.ibPet);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = vh.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Pet pet = list.get(adapterPosition);
                    interfaceModifyPets.gestionClick(adapterPosition, pet);
                }
                NavController navController = Navigation.findNavController(activity, R.id.fragmentContainerView);
                navController.navigate(R.id.action_listPetsFragment_to_modifyPetsFragment, bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Pet> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ibPet;
        TextView tvName;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibPet = itemView.findViewById(R.id.ibPet);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    position = getAdapterPosition();
                    interfaceModifyPets.gestionClick(position, list.get(position));

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", activity.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");
                    String authToken = "Bearer " + token;

                    InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);
                    Call<SimpleApiResponse> call = interfaceServer.deletePet(authToken, list.get(position).getId());

                    call.enqueue(new Callback<SimpleApiResponse>() {
                        @Override
                        public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                            if (response.isSuccessful()) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleApiResponse> call, Throwable t) {
                            Log.d("Pet", "onFailure: " + t.getMessage());
                        }
                    });

                    return false;
                }
            });
        }
    }
}