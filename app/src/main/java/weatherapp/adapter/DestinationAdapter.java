package weatherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.entities.DestinyEntity;
import database.entities.TripEntity;
import com.weatherapp.R;
import weatherapp.ui.EditDestination;
import weatherapp.ui.TripPage;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>{

    private List<DestinyEntity> destinationList;
    private DayAdapter dayAdapter;
    private static Context context;

    // Constructor del adaptador
    public DestinationAdapter(List<DestinyEntity> destinationList, Context context) {
        this.destinationList = destinationList;
        this.context = context;
    }

    public void setDestinationList(List<DestinyEntity> destinationList) {
        this.destinationList=destinationList;
    }

    // ViewHolder para manejar las vistas de cada item
    public static class DestinationViewHolder extends RecyclerView.ViewHolder {
        TextView tvDestinationName;
        private RecyclerView daysRecyclerView;


        public DestinationViewHolder(View itemView) {
            super(itemView);
            tvDestinationName = itemView.findViewById(R.id.textView);

            daysRecyclerView = itemView.findViewById(R.id.dayList); // RecyclerView para los días

            // Configura el RecyclerView de los días
            daysRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_destination_item, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        DestinyEntity destination = destinationList.get(position);
        holder.tvDestinationName.setText(destination.getName());


        // Configuramos el adaptador para los días de este destino
        dayAdapter = new DayAdapter(destination.getDateInfo(), context);
        holder.daysRecyclerView.setAdapter(dayAdapter);

        // Configurar click listener

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditDestination.class);
            intent.putExtra("id", destination.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }
}
