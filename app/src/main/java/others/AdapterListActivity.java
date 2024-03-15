package others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import unique.Pet;
import unique.PetActivity;

public class AdapterListActivity extends RecyclerView.Adapter<AdapterListActivity.ViewHolder>{

    // VARIABLES
    TextView tvInOrOut, tvDate, tvTime;
    ImageView ivPet;
    public interface InterfacePetActivity {
        public void clickManager(int position, PetActivity petActivity);
    }
    InterfacePetActivity interfacePetActivity;
    List<PetActivity> listPetActivity;
    List<Pet> listPet;


    // CONSTRUCTOR
    public AdapterListActivity(List<PetActivity> listPetActivity, InterfacePetActivity interfacePetActivity, List<Pet> listPet) {
        this.listPetActivity = listPetActivity;
        this.interfacePetActivity = interfacePetActivity;
        this.listPet = listPet;
    }


    // METHODS
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pet_activity_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        if (listPetActivity.get(position).isInOrOut() == 0) {
            vh.tvInOrOut.setText("Sortie");
            vh.tvInOrOut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.drawable_output_circle, 0, 0, 0);
        } else {
            vh.tvInOrOut.setText("Entrée");
            vh.tvInOrOut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.drawable_input_circle, 0, 0, 0);
        }
        vh.tvDate.setText(listPetActivity.get(position).getDate());
        vh.tvTime.setText(listPetActivity.get(position).getTime());
        for (Pet pet : listPet) {
            if (listPetActivity.get(position).getCollar_tag().equals(pet.getCollar_tag())) {
                Picasso.get().load(pet.getImg()).into(vh.ivPet);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listPetActivity.size();
    }

    public void addPetActivity(PetActivity petActivity) {
        listPetActivity.add(petActivity);
        notifyItemInserted(listPetActivity.size() - 1);
    }

    public void removePetActivity(int position) {
        listPetActivity.remove(position);
        notifyItemRemoved(position);
    }

    public void editPetActivity(int position, PetActivity petActivity) {
        listPetActivity.set(position, petActivity);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInOrOut, tvDate, tvTime;
        ImageView ivPet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInOrOut = itemView.findViewById(R.id.tvInOrOut);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivPet = itemView.findViewById(R.id.ivPet);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfacePetActivity.clickManager(getAdapterPosition(), listPetActivity.get(getAdapterPosition()));
                }
            });
        }
    }
}
