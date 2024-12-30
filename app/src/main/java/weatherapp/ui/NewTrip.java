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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import database.Dictionary;
import database.dto.NewTripDTO;
import com.weatherapp.R;
import weatherapp.SingletonMap;

public class NewTrip extends AppCompatActivity {

    //ADD DATABASE CONNECTION
    Dictionary dict;

    EditText tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        tripName = findViewById(R.id.editTextTrip);
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
            case R.id.cancel_button: finish(); break;
            case R.id.save_button: createTrip(); break;
        }
    }

    private void createTrip() {
        String name = tripName.getText().toString();
        if(name.isEmpty()) {
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
        dict.insertTrip(new NewTripDTO(name));


        Intent resultIntent = new Intent();
        resultIntent.putExtra("created", true);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    private void initDictionary() {
        dict = (Dictionary) SingletonMap.getInstance().get(HomePage.SHARED_AGENDA);
        if(dict == null) {
            dict = new Dictionary(getApplicationContext());
            SingletonMap.getInstance().put(HomePage.SHARED_AGENDA, dict);
        }
    }
}