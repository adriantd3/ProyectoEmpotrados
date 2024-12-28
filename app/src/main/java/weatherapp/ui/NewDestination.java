package weatherapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import api.tomorrowio.response.TomorrowResponse;
import ssedm.lcc.example.newdictionarywithddbb.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import api.nominatim.GeocodingResponse;
import api.nominatim.NominatimService;
import database.Dictionary;
import database.dto.NewDestinyDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private NominatimService nominatimService;

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
        nominatimService = new NominatimService();

        btnClearDestination.setOnClickListener(v -> editTextDestination.setText(""));

        arrivalDatePicker.setOnClickListener(v -> showDatePickerDialog(arrivalDatePicker));
        departureDatePicker.setOnClickListener(v -> showDatePickerDialog(departureDatePicker));

        btnSave.setOnClickListener(v -> {
            String destination = editTextDestination.getText().toString();

            if (destination.isEmpty()) {
                Toast.makeText(NewDestination.this, "Please enter a destination.", Toast.LENGTH_SHORT).show();
            } else {

                nominatimService.getCoordinates(destination, new Callback<List<GeocodingResponse>>() {
                    @Override
                    public void onResponse(Call<List<GeocodingResponse>> call, Response<List<GeocodingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            GeocodingResponse geocodingResponse = response.body().get(0);

                            NewDestinyDTO newDestiny = new NewDestinyDTO();
                            newDestiny.setName(destination);
                            newDestiny.setArrivalDate(arrivalDate);
                            newDestiny.setDepartureDate(departureDate);
                            newDestiny.setTripId(1);

                            TomorrowResponse tomorrowResponse = new TomorrowResponse();

                            dictionary.insertDestiny(newDestiny, geocodingResponse, tomorrowResponse);

                            Toast.makeText(NewDestination.this, "Destination saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(NewDestination.this, "Geocoding failed or no results found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GeocodingResponse>> call, Throwable t) {
                        Toast.makeText(NewDestination.this, "Error fetching geocoding data.", Toast.LENGTH_SHORT).show();
                    }
                });
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
