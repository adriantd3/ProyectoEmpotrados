package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.Dictionary;
import database.entities.TripEntity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import weatherapp.SingletonMap;
import weatherapp.adapter.TripAdapter;

public class HomePage extends AppCompatActivity {
    public static final String SHARED_AGENDA = "SHARED_AGENDA";
    private ActivityResultLauncher<Intent> launcher;

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;

    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.tripList);

        // Configurar LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dict = new Dictionary(this);

        tripAdapter = new TripAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(tripAdapter);


        loadData();

        initNewTripLauncher();


        //initDictionary();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<TripEntity> tripList = dict.getAllTrips();

        // Configurar el adaptador
        tripAdapter.setTripList(tripList);
        tripAdapter.notifyDataSetChanged();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newTripButton:
                redirectToNewTrip();
                break;

        }
    }

    private void redirectToNewTrip() {
        launcher.launch(new Intent(this, NewTrip.class));
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(HomePage.SHARED_AGENDA);
        if (dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(HomePage.SHARED_AGENDA, dict);
        }
    }

    private void initNewTripLauncher() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getBooleanExtra("created", false)) {
                            Toast toast = Toast.makeText(this, R.string.trip_added, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
        );
    }
}