package weatherapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ssedm.lcc.example.newdictionarywithddbb.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewDestiny extends AppCompatActivity {

    private EditText editTextDestiny;
    private TextView arrivalDatePicker;
    private TextView departureDatePicker;
    private Button btnSave;
    private Button btnCancel;
    private ImageButton btnClearDestiny;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activar Edge to Edge para que la interfaz ocupe toda la pantalla
        setContentView(R.layout.activity_new_destiny); // Establecer el layout de la actividad

        // Inicializar las vistas
        editTextDestiny = findViewById(R.id.editTextDestiny);
        arrivalDatePicker = findViewById(R.id.arrivalDatePicker);
        departureDatePicker = findViewById(R.id.departureDatePicker);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnClearDestiny = findViewById(R.id.btnClearDestiny);

        // Establecer el formato de la fecha
        dateFormat = new SimpleDateFormat("dd/MM/yy");
        calendar = Calendar.getInstance(); // Crear una instancia de calendario

        // Establecer las fechas iniciales
        arrivalDatePicker.setText(dateFormat.format(calendar.getTime()));
        departureDatePicker.setText(dateFormat.format(calendar.getTime()));

        // Configurar el botón de limpiar el destino
        btnClearDestiny.setOnClickListener(v -> editTextDestiny.setText(""));

        // Configurar los pickers de fecha de llegada y salida
        arrivalDatePicker.setOnClickListener(v -> showDatePickerDialog(arrivalDatePicker));
        departureDatePicker.setOnClickListener(v -> showDatePickerDialog(departureDatePicker));

        // Configurar el botón de guardar
        btnSave.setOnClickListener(v -> {
            String destiny = editTextDestiny.getText().toString();
            String arrivalDate = arrivalDatePicker.getText().toString();
            String departureDate = departureDatePicker.getText().toString();

            if (destiny.isEmpty()) {
                Toast.makeText(NewDestiny.this, "Please enter a destiny.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewDestiny.this, "Destiny saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    // Método para mostrar el DatePickerDialog
    private void showDatePickerDialog(TextView targetTextView) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth); // Establecer la fecha seleccionada en el calendario
            targetTextView.setText(dateFormat.format(calendar.getTime())); // Mostrar la fecha en el TextView correspondiente
        };

        // Mostrar el DatePickerDialog para la fecha de llegada o salida
        new DatePickerDialog(
                NewDestiny.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}
