
        package others;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sesameapplication.R;
import java.util.List;
import java.util.Locale;
import reseau_api.InterfaceServer;
import reseau_api.RetrofitInstance;
import reseau_api.SimpleApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unique.LockSchedule;
public class AdapterListSchedule extends RecyclerView.Adapter<AdapterListSchedule.ViewHolder>{

// VARIABLES

    TextView tvDotw, tvStartTime, tvEndTime;

    View div1, div2, div3, div4, div5, div6;

    Switch switch1;
    public interface InterfaceSchedule {
        public void clickManager(int position);

    }

    InterfaceSchedule interfaceSchedule;

    List<LockSchedule> listSchedule;

    Activity activity;

    // CONSTRUCTOR
    public AdapterListSchedule(List<LockSchedule> listSchedule, InterfaceSchedule interfaceSchedule, Activity activity) {
        this.listSchedule = listSchedule;
        this.interfaceSchedule = interfaceSchedule;
        this.activity = activity;

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

        vh.tvDotw.setText(listSchedule.get(position).getDayOfWeek());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            switch (listSchedule.get(position).getDayOfWeek()) {
                case "Monday":

                    vh.tvDotw.setText("Lundi");
                    break;
                case "Tuesday":

                    vh.tvDotw.setText("Mardi");
                    break;
                case "Wednesday":

                    vh.tvDotw.setText("Mercredi");
                    break;
                case "Thursday":

                    vh.tvDotw.setText("Jeudi");
                    break;
                case "Friday":

                    vh.tvDotw.setText("Vendredi");
                    break;
                case "Saturday":

                    vh.tvDotw.setText("Samedi");
                    break;
                case "Sunday":

                    vh.tvDotw.setText("Dimanche");
                    break;

            }

        } else

            vh.tvDotw.setText(listSchedule.get(position).getDayOfWeek());

        vh.tvEndTime.setText(listSchedule.get(position).getCloseTime());

        vh.tvStartTime.setText(listSchedule.get(position).getOpenTime());
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

        vh.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", activity.MODE_PRIVATE);

                String token = sharedPreferences.getString("token", "");

                String authToken = "Bearer " + token;

                InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);

                Call<LockSchedule> call = interfaceServer.updateRecurring(authToken, listSchedule.get(position).getId());

                call.enqueue(new Callback<LockSchedule>() {
                    @Override
                    public void onResponse(Call<LockSchedule> call, Response<LockSchedule> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(activity, "Recurring status updated", Toast.LENGTH_SHORT).show();

                        }
                        else

                            Log.d("LockSchedule", "onResponse: " + response.errorBody());

                    }
                    @Override
                    public void onFailure(Call<LockSchedule> call, Throwable t) {

                        Log.d("LockSchedule", "onFailure: " + t.getMessage());

                    }

                });

            }

        });

    }
    @Override
    public int getItemCount() {
        return listSchedule.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDotw, tvStartTime, tvEndTime;

        View div1, div2, div3, div4, div5, div6;

        Switch switch1;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDotw = itemView.findViewById(R.id.tvDotw);

            tvStartTime = itemView.findViewById(R.id.tvStartTime);

            tvEndTime = itemView.findViewById(R.id.tvEndTime);

            div1 = itemView.findViewById(R.id.div1);

            div2 = itemView.findViewById(R.id.div2);

            div3 = itemView.findViewById(R.id.div3);

            div4 = itemView.findViewById(R.id.div4);

            div5 = itemView.findViewById(R.id.div5);

            div6 = itemView.findViewById(R.id.div6);

            switch1 = itemView.findViewById(R.id.switch1);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    position = getAdapterPosition();

                    interfaceSchedule.clickManager(position);

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", activity.MODE_PRIVATE);

                    String token = sharedPreferences.getString("token", "");

                    String authToken = "Bearer " + token;

                    InterfaceServer interfaceServer = RetrofitInstance.getInstance().create(InterfaceServer.class);

                    Call<SimpleApiResponse> call = interfaceServer.deleteLockSchedule(authToken, listSchedule.get(position).getId());

                    call.enqueue(new Callback<SimpleApiResponse>() {
                        @Override
                        public void onResponse(Call<SimpleApiResponse> call, Response<SimpleApiResponse> response) {
                            if (response.isSuccessful()) {

                                listSchedule.remove(position);

                                notifyItemRemoved(position);

                                notifyItemRangeChanged(position, listSchedule.size());

                            }

                        }
                        @Override
                        public void onFailure(Call<SimpleApiResponse> call, Throwable t) {

                            Log.d("LockSchedule", "onFailure: " + t.getMessage());

                        }

                    });
                    return false;

                }

            });

        }

    }

}
