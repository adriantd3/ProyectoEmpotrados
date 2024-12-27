package weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ssedm.lcc.example.newdictionarywithddbb.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
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

    // Temporary method
    private void redirectToNewDestiny() {
        Intent it = new Intent(this, NewDestiny.class);
        startActivity(it);
    }
}