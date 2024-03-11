package others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;

import java.util.List;

import unique.PetActivity;

public class AdapterListActivity extends RecyclerView.Adapter<AdapterListActivity.ViewHolder>{
    TextView tvInOrOut, tvDate, tvTime;
    ImageView ivPet;

    public interface InterfacePetActivity {
        public void clickManager(int position, PetActivity petActivity);
    }
    InterfacePetActivity interfacePetActivity;
    List<PetActivity> listPetActivity;

    public AdapterListActivity(List<PetActivity> listPetActivity, InterfacePetActivity interfacePetActivity) {
        this.listPetActivity = listPetActivity;
        this.interfacePetActivity = interfacePetActivity;
    }

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
        vh.tvInOrOut.setText(listPetActivity.get(position).isInOrOut() ? "Entrée" : "Sortie");
        vh.tvInOrOut.setCompoundDrawablesWithIntrinsicBounds(listPetActivity.get(position).isInOrOut() ? R.drawable.drawable_input_circle : R.drawable.drawable_output_circle, 0, 0, 0);
        vh.tvDate.setText(listPetActivity.get(position).getDate());
        vh.tvTime.setText(listPetActivity.get(position).getTime());
        String imgSrc = listPetActivity.get(position).getPet();
        int imgId = vh.ivPet.getResources().getIdentifier(imgSrc, "mipmap", vh.ivPet.getContext().getPackageName());
        vh.ivPet.setImageResource(imgId);
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
