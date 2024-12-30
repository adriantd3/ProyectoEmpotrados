package weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.entities.DateInfoEntity;
import ssedm.lcc.example.newdictionarywithddbb.R;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder>{

    private List<DateInfoEntity> dayList;
    private static Context context;

    // Constructor del adaptador
    public DayAdapter(List<DateInfoEntity> dayList, Context context) {
        this.dayList = dayList;
        this.context = context;
    }

    // ViewHolder para manejar las vistas de cada item
    public static class DayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTemp;
        private ImageView weatherImageView;

        public DayViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.textView2);
            tvTemp = itemView.findViewById(R.id.textView3);
            weatherImageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_day_item, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DateInfoEntity day = dayList.get(position);
        holder.tvDate.setText(day.getDate().toString());

        holder.tvTemp.setText(String.valueOf(day.getTmp()) + "ºC");

        int iconCode = day.getWeatherCode();

        if (iconCode != 0) {
            holder.weatherImageView.setImageResource(context.getResources().getIdentifier("w_"+iconCode, "drawable", context.getPackageName()));
        } else {
            holder.weatherImageView.setImageResource(R.drawable.black_border_shape);
        }

        /*

        Glide.with(context)
                .load(day.getImageUrl()) // Usamos Glide para cargar imágenes
                .into(weatherImageView);
         */


        /*
        // Configurar click listener

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TripPage.class);
            intent.putExtra("id", trip.getId());
            intent.putExtra("name", trip.getName());
            context.startActivity(intent);
        });
         */
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }
}
