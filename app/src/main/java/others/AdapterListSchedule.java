package others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesameapplication.R;

import java.util.List;

import unique.LockSchedule;

public class AdapterListSchedule extends RecyclerView.Adapter<AdapterListSchedule.ViewHolder>{

    // VARIABLES
    TextView tvDimanche, tvLundi, tvMardi, tvMercredi, tvJeudi, tvVendredi, tvSamedi, tvStartTime, tvEndTime;
    View div1, div2, div3, div4, div5, div6;
    Switch switch1;
    public interface InterfaceSchedule {
        public void clickManager(int position);
    }
    InterfaceSchedule interfaceSchedule;
    List<LockSchedule> listSchedule;

    // CONSTRUCTOR
    public AdapterListSchedule(List<LockSchedule> listSchedule, InterfaceSchedule interfaceSchedule) {
        this.listSchedule = listSchedule;
        this.interfaceSchedule = interfaceSchedule;
    }

    @NonNull
    @Override
    public AdapterListSchedule.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lock_schedule_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListSchedule.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        if (!listSchedule.get(position).getDayOfWeek().equals("Sunday")) {
            vh.tvDimanche.setVisibility(View.GONE);
            vh.div1.setVisibility(View.GONE);
        }
        else {
            vh.tvDimanche.setText("Dimanche");
            vh.tvDimanche.setVisibility(View.VISIBLE);
            vh.div1.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Monday")) {
            vh.tvLundi.setVisibility(View.GONE);
            vh.div2.setVisibility(View.GONE);
        }
        else {
            vh.tvLundi.setText("Lundi");
            vh.tvLundi.setVisibility(View.VISIBLE);
            vh.div2.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Tuesday")) {
            vh.tvMardi.setVisibility(View.GONE);
            vh.div3.setVisibility(View.GONE);
        }
        else {
            vh.tvMardi.setText("Mardi");
            vh.tvMardi.setVisibility(View.VISIBLE);
            vh.div3.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Wednesday")) {
            vh.tvMercredi.setVisibility(View.GONE);
            vh.div4.setVisibility(View.GONE);
        }
        else {
            vh.tvMercredi.setText("Mercredi");
            vh.tvMercredi.setVisibility(View.VISIBLE);
            vh.div4.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Thursday")) {
            vh.tvJeudi.setVisibility(View.GONE);
            vh.div5.setVisibility(View.GONE);
        }
        else {
            vh.tvJeudi.setText("Jeudi");
            vh.tvJeudi.setVisibility(View.VISIBLE);
            vh.div5.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Friday")) {
            vh.tvVendredi.setVisibility(View.GONE);
            vh.div6.setVisibility(View.GONE);
        }
        else {
            vh.tvVendredi.setText("Vendredi");
            vh.tvVendredi.setVisibility(View.VISIBLE);
            vh.div6.setVisibility(View.VISIBLE);
        }

        if (!listSchedule.get(position).getDayOfWeek().equals("Saturday")) {
            vh.tvSamedi.setVisibility(View.GONE);
        }
        else {
            vh.tvSamedi.setText("Samedi");
            vh.tvSamedi.setVisibility(View.VISIBLE);
        }

        vh.tvStartTime.setText(listSchedule.get(position).getCloseTime());
        vh.tvEndTime.setText(listSchedule.get(position).getOpenTime());

        if (listSchedule.get(position).getRecurring() == 0) {
            vh.switch1.setChecked(false);
        } else {
            vh.switch1.setChecked(true);
        }

        // Handle switch state change listener
        vh.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.switch1.setChecked(!vh.switch1.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSchedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDimanche, tvLundi, tvMardi, tvMercredi, tvJeudi, tvVendredi, tvSamedi, tvStartTime, tvEndTime;
        View div1, div2, div3, div4, div5, div6;
        Switch switch1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDimanche = itemView.findViewById(R.id.tvDimanche);
            tvLundi = itemView.findViewById(R.id.tvLundi);
            tvMardi = itemView.findViewById(R.id.tvMardi);
            tvMercredi = itemView.findViewById(R.id.tvMercredi);
            tvJeudi = itemView.findViewById(R.id.tvJeudi);
            tvVendredi = itemView.findViewById(R.id.tvVendredi);
            tvSamedi = itemView.findViewById(R.id.tvSamedi);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            div1 = itemView.findViewById(R.id.div1);
            div2 = itemView.findViewById(R.id.div2);
            div3 = itemView.findViewById(R.id.div3);
            div4 = itemView.findViewById(R.id.div4);
            div5 = itemView.findViewById(R.id.div5);
            div6 = itemView.findViewById(R.id.div6);
            switch1 = itemView.findViewById(R.id.switch1);
        }
    }
}
