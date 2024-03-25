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

import java.util.List;

import unique.Pet;

public class AdapterListModifyPets extends RecyclerView.Adapter<AdapterListModifyPets.ViewHolder>{

    public interface InterfaceModifyPets{
        public void gestionClick(int position, Pet pet);
    }

    InterfaceModifyPets interfaceModifyPets;
    List<Pet> list;

    public AdapterListModifyPets(List<Pet> list, InterfaceModifyPets interfaceModifyPets)
    {
        this.list = list;
        this.interfaceModifyPets = interfaceModifyPets;
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
        String img = pet.getImg();
        String name = pet.getName();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibPet = itemView.findViewById(R.id.ibPet);
            tvName = itemView.findViewById(R.id.tvName);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //call delete pets
                    return false;
                }
            });
        }
    }
}
