package weatherapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import api.tomorrowio.TomorrowioService;
import api.tomorrowio.response.TomorrowResponse;
import database.entities.DestinyEntity;
import database.Dictionary;
import api.nominatim.GeocodingResponse;
import api.nominatim.NominatimService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ssedm.lcc.example.newdictionarywithddbb.R;

public class EditDestination extends AppCompatActivity {

    private EditText editTextDestination;
    private TextView arrivalDatePicker;
    private TextView departureDatePicker;
    private Button btnSave;
    private Button btnCancel;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private DateTimeFormatter dateFormatter;

    private Dictionary dictionary;
    private NominatimService nominatimService;
    private TomorrowioService tomorrowioService;
    private static Integer destinyId = 11;
    private DestinyEntity destiny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_destination);

        editTextDestination = findViewById(R.id.editTextDestination);
        arrivalDatePicker = findViewById(R.id.arrivalDatePicker);
        departureDatePicker = findViewById(R.id.departureDatePicker);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        arrivalDate = LocalDate.now();
        departureDate = LocalDate.now();

        dictionary = new Dictionary(this);
        nominatimService = new NominatimService();
        tomorrowioService = new TomorrowioService();

        destiny = dictionary.getDestinyById(destinyId);
        setTitle("Edit Destination - " + destiny.getName());
        editTextDestination.setText(destiny.getName());
        arrivalDate = destiny.getArrivalDate();
        departureDate = destiny.getDepartureDate();

        arrivalDatePicker.setText(arrivalDate.format(dateFormatter));
        departureDatePicker.setText(departureDate.format(dateFormatter));

        arrivalDatePicker.setOnClickListener(v -> showDatePickerDialog(arrivalDatePicker));
        departureDatePicker.setOnClickListener(v -> showDatePickerDialog(departureDatePicker));

        btnSave.setOnClickListener(v -> {
            String updatedDestination = editTextDestination.getText().toString();

            if (updatedDestination.isEmpty()) {
                Toast.makeText(EditDestination.this, "Please enter a destination.", Toast.LENGTH_SHORT).show();
            } else {
                destiny.setName(updatedDestination);
                destiny.setArrivalDate(arrivalDate);
                destiny.setDepartureDate(departureDate);

                nominatimService.getCoordinates(updatedDestination, new Callback<List<GeocodingResponse>>() {
                    @Override
                    public void onResponse(Call<List<GeocodingResponse>> call, Response<List<GeocodingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            GeocodingResponse geocodingResponse = response.body().get(0);

                            String formattedStartDate = arrivalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            String formattedEndDate = departureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            LocalDate startTime = LocalDate.parse(formattedStartDate);
                            LocalDate endTime = LocalDate.parse(formattedEndDate);

                            tomorrowioService.getWeatherData(geocodingResponse, startTime, endTime, new Callback<>() {
                                @Override
                                public void onResponse(Call<TomorrowResponse> call, Response<TomorrowResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        TomorrowResponse tomorrowResponse = response.body();
                                        dictionary.updateDestiny(destiny, geocodingResponse, tomorrowResponse);
                                        Toast.makeText(EditDestination.this, "Destination updated successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        try {
                                            String errorBody = response.errorBody().string();
                                            Log.e("API Error", errorBody);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(EditDestination.this, "Incorrect dates", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<TomorrowResponse> call, Throwable t) {
                                    Log.e("API Error", t.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(EditDestination.this, "Geocoding failed or no results found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GeocodingResponse>> call, Throwable t) {
                        Toast.makeText(EditDestination.this, "Error fetching geocoding data.", Toast.LENGTH_SHORT).show();
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
                destiny.setArrivalDate(arrivalDate);
            } else {
                departureDate = selectedDate;
                targetTextView.setText(departureDate.format(dateFormatter));
                destiny.setDepartureDate(departureDate);
            }
        };

        new DatePickerDialog(
                EditDestination.this,
                dateSetListener,
                arrivalDate.getYear(),
                arrivalDate.getMonthValue() - 1,
                arrivalDate.getDayOfMonth()
        ).show();
    }
}
