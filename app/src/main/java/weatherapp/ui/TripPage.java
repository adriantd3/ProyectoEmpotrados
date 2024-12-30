package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import database.Dictionary;
import database.entities.DestinyEntity;
import database.entities.TripEntity;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;
import weatherapp.adapter.DestinationAdapter;
import weatherapp.adapter.TripAdapter;

public class TripPage extends AppCompatActivity {
    public static final String SHARED_AGENDA = "SHARED_AGENDA";

    private Dictionary dict;
    private String tripName;
    private int tripId;

    private RecyclerView recyclerView;
    private DestinationAdapter destinationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        tripName = getIntent().getStringExtra("name");
        if(tripName!=null) setTitle(tripName);

        this.tripId = getIntent().getIntExtra("id", 0);


        recyclerView = findViewById(R.id.destinationList);

        // Configurar LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dict = new Dictionary(this);


        // Configurar el adaptador
        destinationAdapter = new DestinationAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(destinationAdapter);

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    private void loadData() {
        List<DestinyEntity> destinationList = dict.getAllByTripId(this.tripId);

        // Configurar el adaptador
        destinationAdapter.setDestinationList(destinationList);
        destinationAdapter.notifyDataSetChanged();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newDestinationButton:
                redirectToNewdestination();
                break;
            case R.id.editTripButton:
                redirectToEditTrip();
                break;
            case R.id.deleteTripButton:
                redirectToDeleteTrip();
                break;
        }
    }

    private void redirectToEditTrip() {
        //Intent it = new Intent(this, NewTrip.class);
        //startActivity(it);
    }

    private void redirectToDeleteTrip() {
        //Intent it = new Intent(this, NewTrip.class);
        //startActivity(it);
    }

    // Temporary redirect to Newdestination
    private void redirectToNewdestination() {
        Intent it = new Intent(this, NewDestination.class);
        startActivity(it);
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(TripPage.SHARED_AGENDA, dict);
        }
    }
}