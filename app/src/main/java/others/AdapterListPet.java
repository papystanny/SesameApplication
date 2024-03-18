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
import unique.PetActivity;

public class AdapterListPet extends RecyclerView.Adapter<AdapterListPet.ViewHolder>{

    // VARIABLES
    TextView tvName, tvNickName, isOutside, tvTotalActivity, tvLastActivity;
    ImageView ivPet, ibNext, ibPrevious;
    public interface InterfacePet {
        public void clickManager(int position);
    }
    InterfacePet interfacePet;
    List<Pet> listPet;
    List<PetActivity> listPetActivity;


    // CONSTRUCTOR
    public AdapterListPet(List<Pet> listPet, InterfacePet interfacePet, List<PetActivity> listPetActivity) {
        this.listPet = listPet;
        this.interfacePet = interfacePet;
        this.listPetActivity = listPetActivity;
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

        for (PetActivity petActivity : listPetActivity) {
            if (petActivity.getCollar_tag().equals(listPet.get(position).getCollar_tag()) && petActivity.isInOrOut() == 1) {
                vh.tvLastActivity.setText("Dernière sortie : " + petActivity.getTime());
            }
            if (petActivity.getCollar_tag().equals(listPet.get(position).getCollar_tag())) {
                vh.tvTotalActivity.setText(listPet.get(position).getName() + " est sortie : " + petActivity.getTotalActivity() + " aujourd'hui");
            }
        }

        // Check if there are more than one pet in the list
        if (listPet.size() > 1) {
            // Check if there is a pet after the current one
            if (position < listPet.size() - 1) {
                vh.ibNext.setVisibility(View.VISIBLE);
            } else {
                vh.ibNext.setVisibility(View.GONE);
            }

            // Check if there is a pet before the current one
            if (position > 0) {
                vh.ibPrevious.setVisibility(View.VISIBLE);
            } else {
                vh.ibPrevious.setVisibility(View.GONE);
            }
        } else {
            // Hide both buttons if there is only one pet in the list
            vh.ibNext.setVisibility(View.GONE);
            vh.ibPrevious.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNickName, isOutside, tvTotalActivity, tvLastActivity;
        ImageView ivPet, ibNext, ibPrevious;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameCompagnon);
            tvNickName = itemView.findViewById(R.id.tvNicknameCompagnon);
            isOutside = itemView.findViewById(R.id.tvStatus);
            tvTotalActivity = itemView.findViewById(R.id.tvTimeOut);
            tvLastActivity = itemView.findViewById(R.id.tvLastOut);
            ivPet = itemView.findViewById(R.id.ivPetCompagnon);
            ibNext = itemView.findViewById(R.id.btNextPage);
            ibPrevious = itemView.findViewById(R.id.btPreviousPage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfacePet.clickManager(getAdapterPosition());
                }
            });
        }
    }
}
