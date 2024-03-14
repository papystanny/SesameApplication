package others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import unique.Pet;

public class AdapterListHomePage extends RecyclerView.Adapter<AdapterListHomePage.ViewHolder>{



    public interface InterfacePet{
        public void gestionClick(int position, Pet pet);
    }

    InterfacePet interfacePet;
    List<Pet> list;

    public AdapterListHomePage(List<Pet> list, InterfacePet interfacePet)
    {
        this.list = list;
        this.interfacePet = interfacePet;
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
        vh.tvStatus.setText(list.get(position).getIsOutside() ? "Entrée" : "Sortie");
        Picasso.get().load(list.get(position).getImg()).into(vh.ibPet);
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
        ImageButton ibPet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            /*ibPet = itemView.findViewById(R.id.ivPetPhoto);


            ibPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfacePet.gestionClick(getLayoutPosition(), list.get(getLayoutPosition()));

                }
            });*/

            tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfacePet.gestionClick(getLayoutPosition(), list.get(getLayoutPosition()));
                }
            });
        }
    }
}
