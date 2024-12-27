package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import database.Dictionary;
import database.dto.NewTripDTO;
import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;
import ssedm.lcc.example.newdictionarywithddbb.SingletonMap;

public class NewTrip extends AppCompatActivity {

    //ADD DATABASE CONNECTION
    Dictionary dict;

    TextInputEditText tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        tripName = findViewById(R.id.trip_name_input);
        initDictionary();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Finaliza la actividad actual y vuelve a la anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button: goBack(); break;
            case R.id.save_button: createTrip(); break;
        }
    }

    private void goBack() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    private void createTrip() {
        String name = tripName.getText().toString();
        dict.insertTrip(new NewTripDTO(name));

        finish();
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(HomePage.SHARED_AGENDA, dict);
        }
    }
}