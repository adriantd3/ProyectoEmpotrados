package weatherapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import database.Dictionary;
import database.entities.TripEntity;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;

public class EditTrip extends AppCompatActivity {

    private Dictionary dict;
    private EditText tripNameEditText;
    private int tripId;
    private TripEntity trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        tripNameEditText = findViewById(R.id.editTextTrip);
        initDictionary();

        Intent intent = getIntent();
        tripId = intent.getIntExtra("id", 0);
        trip = dict.getTripById(tripId);
        setTitle("Edit Trip - " + trip.getName());
        String currentName = trip.getName();

        if (currentName != null) {
            tripNameEditText.setText(currentName);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                finish();
                break;
            case R.id.save_button:
                updateTripName();
                break;
        }
    }

    private void updateTripName() {
        String newName = tripNameEditText.getText().toString().trim();

        if (newName.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.error);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setMessage(R.string.trip_name_error);
            builder.show();

            return;
        }

        trip.setName(newName);
        dict.updateTrip(trip);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated", true);
        resultIntent.putExtra("newName", newName);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, R.string.trip_updated, Toast.LENGTH_SHORT).show();

        finish();
    }


    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if (dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(HomePage.SHARED_AGENDA, dict);
        }
    }
}
