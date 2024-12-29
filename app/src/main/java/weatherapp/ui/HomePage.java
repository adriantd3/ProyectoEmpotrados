package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import database.Dictionary;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;

public class HomePage extends AppCompatActivity {
    public static final String SHARED_AGENDA = "SHARED_AGENDA";
    private ActivityResultLauncher<Intent> launcher;

    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initNewTripLauncher();

        initDictionary();
    }

    private void loadTripList() {
        // Cargar la lista de viajes
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newTripButton:
                redirectToNewTrip();
                break;
            case R.id.newDestinyButton:
                redirectToNewDestination();
                break;
        }
    }

    private void redirectToNewTrip() {
        launcher.launch(new Intent(this, NewTrip.class));
    }

    // Temporary redirect to NewDestination
    private void redirectToNewDestination() {
        //launcher.launch(new Intent(this, NewDestination.class));
        launcher.launch(new Intent(this, EditDestination.class));
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
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