package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    public static final int REQUEST_CODE_EDIT_TRIP = 1;

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

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
        Intent intent = new Intent(this, EditTrip.class);
        intent.putExtra("id", tripId);
        startActivityForResult(intent, REQUEST_CODE_EDIT_TRIP);

    }

    private void redirectToDeleteTrip() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.delete_trip_title)
                .setMessage(R.string.delete_trip_confirmation)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    dict.deleteTrip(tripId);
                    Toast.makeText(this, R.string.trip_deleted, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void redirectToNewdestination() {
        Intent it = new Intent(this, NewDestination.class);
        it.putExtra("id", tripId);
        startActivity(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_TRIP && resultCode == RESULT_OK) {
            boolean updated = data.getBooleanExtra("updated", false);
            if (updated) {
                String updatedName = data.getStringExtra("newName");
                setTitle(updatedName);
            }
        }
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(TripPage.SHARED_AGENDA, dict);
        }
    }
}