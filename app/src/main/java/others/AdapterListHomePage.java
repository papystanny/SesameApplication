package others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import unique.Pet;
import unique.PetActivity;

public class AdapterListHomePage extends RecyclerView.Adapter<AdapterListHomePage.ViewHolder>{



    public interface InterfacePet{
        public void gestionClick(int position, Pet pet);
    }

    InterfacePet interfacePet;
    List<Pet> list;
    List<PetActivity> listActivity;

    public AdapterListHomePage(List<Pet> list, InterfacePet interfacePet, List<PetActivity> listActivity)
    {
        this.list = list;
        this.interfacePet = interfacePet;
        this.listActivity = listActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_pet_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Collections.reverse(listActivity);
        for (PetActivity petActivity : listActivity) {
            if (petActivity.getCollar_tag().equals(list.get(position).getCollar_tag()) && petActivity.isInOrOut() == 1) {
                if (Locale.getDefault().getLanguage().equals("fr"))
                    vh.tvStatus.setText("Sortie");
                else
                    vh.tvStatus.setText("Outside");
            }
            if (petActivity.getCollar_tag().equals(list.get(position).getCollar_tag()) && petActivity.isInOrOut() == 0) {
                if (Locale.getDefault().getLanguage().equals("fr"))
                    vh.tvStatus.setText("Entrée");
                else
                    vh.tvStatus.setText("Inside");
            }
        }
        Picasso.get().load(list.get(position).getImg()).into(vh.ivPet);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void ajouterPet(Pet pet)
    {
        list.add(pet);
        notifyItemInserted(list.size() - 1);
    }
    public void supprimerPet(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvStatus;
        ImageView ivPet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivPet = itemView.findViewById(R.id.ivPetPhoto);


            ivPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfacePet.gestionClick(getLayoutPosition(), list.get(getLayoutPosition()));
                }
            });
            tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfacePet.gestionClick(getLayoutPosition(), list.get(getLayoutPosition()));
                }
            });
        }
    }
}
