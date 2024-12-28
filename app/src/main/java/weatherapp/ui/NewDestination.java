package weatherapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import api.tomorrowio.response.TomorrowResponse;
import database.dto.NewTripDTO;
import ssedm.lcc.example.newdictionarywithddbb.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import api.nominatim.GeocodingResponse;
import database.Dictionary;
import database.dto.NewDestinyDTO;

public class NewDestination extends AppCompatActivity {

    private EditText editTextDestination;
    private TextView arrivalDatePicker;
    private TextView departureDatePicker;
    private Button btnSave;
    private Button btnCancel;
    private ImageButton btnClearDestination;

    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private DateTimeFormatter dateFormatter;

    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_destination);

        editTextDestination = findViewById(R.id.editTextDestination);
        arrivalDatePicker = findViewById(R.id.arrivalDatePicker);
        departureDatePicker = findViewById(R.id.departureDatePicker);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnClearDestination = findViewById(R.id.btnClearDestination);

        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        arrivalDate = LocalDate.now();
        departureDate = LocalDate.now();

        arrivalDatePicker.setText(arrivalDate.format(dateFormatter));
        departureDatePicker.setText(departureDate.format(dateFormatter));

        dictionary = new Dictionary(this);

        btnClearDestination.setOnClickListener(v -> editTextDestination.setText(""));

        arrivalDatePicker.setOnClickListener(v -> showDatePickerDialog(arrivalDatePicker));
        departureDatePicker.setOnClickListener(v -> showDatePickerDialog(departureDatePicker));

        btnSave.setOnClickListener(v -> {
            String destination = editTextDestination.getText().toString();

            if (destination.isEmpty()) {
                Toast.makeText(NewDestination.this, "Please enter a destination.", Toast.LENGTH_SHORT).show();
            } else {
                NewTripDTO trip = new NewTripDTO("test");
                dictionary.insertTrip(trip);


                NewDestinyDTO newDestiny = new NewDestinyDTO();
                newDestiny.setName(destination);
                newDestiny.setArrivalDate(arrivalDate);
                newDestiny.setDepartureDate(departureDate);
                newDestiny.setTripId(1);

                GeocodingResponse geocodingResponse = new GeocodingResponse();
                geocodingResponse.setLat(0.0f);
                geocodingResponse.setLon(0.0f);

                TomorrowResponse tomorrowResponse = new TomorrowResponse();

                dictionary.insertDestiny(newDestiny, geocodingResponse, tomorrowResponse);

                Toast.makeText(NewDestination.this, "Destination saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog(TextView targetTextView) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            if (targetTextView == arrivalDatePicker) {
                arrivalDate = selectedDate;
                targetTextView.setText(arrivalDate.format(dateFormatter));
            } else {
                departureDate = selectedDate;
                targetTextView.setText(departureDate.format(dateFormatter));
            }
        };

        new DatePickerDialog(
                NewDestination.this,
                dateSetListener,
                arrivalDate.getYear(),
                arrivalDate.getMonthValue() - 1,
                arrivalDate.getDayOfMonth()
        ).show();
    }
}
