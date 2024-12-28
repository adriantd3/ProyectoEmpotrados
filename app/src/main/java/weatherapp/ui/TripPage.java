package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import database.Dictionary;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;

public class TripPage extends AppCompatActivity {
    public static final String SHARED_AGENDA = "SHARED_AGENDA";

    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        initDictionary();
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