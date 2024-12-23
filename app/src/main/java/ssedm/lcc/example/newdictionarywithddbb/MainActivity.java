package ssedm.lcc.example.newdictionarywithddbb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "agendaDB.db";
    public static final String SHARED_AGENDA = "SHARED_AGENDA";

    // Resto de atributos
    private EditText editText;
    private TextView textView1;
    private TextView textView2;
    private Dictionary dictionary;


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: search_word(); break;
            case R.id.addButton: redirect(); break;
        }
    }

    private void redirect(){
        Intent it = new Intent(this, AddContactActivity.class);
        startActivity(it);
    }

    private void initDictionary() {
        dictionary = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dictionary == null) {
            dictionary = new Dictionary(getApplicationContext(), MainActivity.DATABASE_NAME);
            SingletonMap.getInstance().put(MainActivity.SHARED_AGENDA, dictionary);
        }
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @SuppressLint("Range")
    public void search_word() {
        String word = editText.getText().toString().trim().toLowerCase();
        if (word.isEmpty()) {
            Toast.makeText(this, R.string.badInput, Toast.LENGTH_LONG).show();
        } else {
            String meaning = dictionary.searchMeaning(word);

            textView1.setText(word);
            if (meaning.isEmpty()) {
                textView2.setText(R.string.wordNotFound);
            } else {
                textView2.setText(meaning);
            }
            editText.setText("");
        }
        hideSoftKeyboard(editText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        initDictionary();
        dictionary.initDict();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dictionary.finalize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}