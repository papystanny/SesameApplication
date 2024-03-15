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

public class AdapterListPet extends RecyclerView.Adapter<AdapterListPet.ViewHolder>{

    // VARIABLES
    TextView tvName, tvNickName, isOutside, tvTotalActivity, tvLastActivity;
    ImageView ivPet;
    public interface InterfacePet {
        public void clickManager(int position);
    }
    InterfacePet interfacePet;
    List<Pet> listPet;


    // CONSTRUCTOR
    public AdapterListPet(List<Pet> listPet, InterfacePet interfacePet) {
        this.listPet = listPet;
        this.interfacePet = interfacePet;
    }


    // METHODS
    @NonNull
    @Override
    public AdapterListPet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pet_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListPet.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.tvName.setText(listPet.get(position).getName());
        vh.tvNickName.setText(listPet.get(position).getNickname());
        if (listPet.get(position).getIsOutside() == 0) {
            vh.isOutside.setText("Entrée");
            vh.isOutside.setCompoundDrawablesWithIntrinsicBounds(R.drawable.drawable_input_circle_home, 0, 0, 0);
        } else {
            vh.isOutside.setText("Sortie");
            vh.isOutside.setCompoundDrawablesWithIntrinsicBounds(R.drawable.drawable_output_circle_home, 0, 0, 0);
        }
        Picasso.get().load(listPet.get(position).getImg()).into(vh.ivPet);
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNickName, isOutside, tvTotalActivity, tvLastActivity;
        ImageView ivPet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameCompagnon);
            tvNickName = itemView.findViewById(R.id.tvNicknameCompagnon);
            isOutside = itemView.findViewById(R.id.tvStatus);
            tvTotalActivity = itemView.findViewById(R.id.tvTimeOut);
            tvLastActivity = itemView.findViewById(R.id.tvLastOut);
            ivPet = itemView.findViewById(R.id.ivPetCompagnon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfacePet.clickManager(getAdapterPosition());
                }
            });
        }
    }
}
