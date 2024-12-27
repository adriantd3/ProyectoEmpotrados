package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import database.Dictionary;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;

public class HomePage extends AppCompatActivity {
    public static final String SHARED_AGENDA = "SHARED_AGENDA";

    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initDictionary();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newTripButton:
                redirectToNewTrip();
                break;
            case R.id.newDestinyButton:
                redirectToNewDestiny();
                break;
        }
    }

    private void redirectToNewTrip() {
        Intent it = new Intent(this, NewTrip.class);
        startActivity(it);
    }

    // Temporary redirect to NewDestiny
    private void redirectToNewDestiny() {
        Intent it = new Intent(this, NewDestiny.class);
        startActivity(it);
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(HomePage.SHARED_AGENDA, dict);
        }
    }
}