package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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