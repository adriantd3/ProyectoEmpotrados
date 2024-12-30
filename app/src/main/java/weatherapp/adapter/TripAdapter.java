package weatherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ssedm.lcc.example.newdictionarywithddbb.R;

import java.util.List;

import database.entities.TripEntity;
import weatherapp.ui.TripPage;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder>{

    private List<TripEntity> tripList;
    private Context context;

    // Constructor del adaptador
    public TripAdapter(List<TripEntity> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    // ViewHolder para manejar las vistas de cada item
    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDates, tvDestinies, tvMinTemp, tvAvgTemp, tvMaxTemp;

        public TripViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView);        // Trip name
            tvDates = itemView.findViewById(R.id.textView2);      // Date range
            tvDestinies = itemView.findViewById(R.id.textView3);  // Number of destinies
            tvMinTemp = itemView.findViewById(R.id.textView4);    // Min temperature
            tvAvgTemp = itemView.findViewById(R.id.textView5);    // Avg temperature
            tvMaxTemp = itemView.findViewById(R.id.textView6);    // Max temperature
        }
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        TripEntity trip = tripList.get(position);
        holder.tvName.setText(trip.getName());
        if (trip.getInitDate()!=null && trip.getEndDate()!=null) holder.tvDates.setText(trip.getInitDate().toString() + " - " + trip.getEndDate().toString());
        if (trip.getNDestinies()!=null) holder.tvDestinies.setText(context.getResources().getQuantityString(
                R.plurals.number_of_destinations, trip.getNDestinies(), trip.getNDestinies()));
        if (trip.getMinTmp()!=null) holder.tvMinTemp.setText(String.format("%.1fºC", trip.getMinTmp()));
        if (trip.getAvgTmp()!=null) holder.tvAvgTemp.setText(String.format("%.1fºC", trip.getAvgTmp()));
        if (trip.getMaxTmp()!=null) holder.tvMaxTemp.setText(String.format("%.1fºC", trip.getMaxTmp()));

        // Configurar click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TripPage.class);
            intent.putExtra("id", trip.getId());
            intent.putExtra("name", trip.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
