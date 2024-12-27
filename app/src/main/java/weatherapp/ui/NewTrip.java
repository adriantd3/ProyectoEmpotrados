package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import ssedm.lcc.example.newdictionarywithddbb.MainActivity;
import ssedm.lcc.example.newdictionarywithddbb.R;

public class NewTrip extends AppCompatActivity {

    //ADD DATABASE CONNECTION

    TextInputEditText tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        tripName = findViewById(R.id.trip_name_input);

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
        // TO DO
    }
}