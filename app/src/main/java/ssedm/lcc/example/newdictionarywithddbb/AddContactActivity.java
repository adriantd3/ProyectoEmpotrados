package ssedm.lcc.example.newdictionarywithddbb;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.DisplayCutoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class AddContactActivity extends AppCompatActivity {

    Dictionary dictionary;

    EditText inputWord, inputMeaning;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputWord = (EditText) findViewById(R.id.word);
        inputMeaning = (EditText) findViewById(R.id.meaning);
        initDictionary();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addWord: addWord(); break;
            case R.id.buttonBack: redirect(); break;
        }
    }

    private void redirect(){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    private void addWord(){

        String word = inputWord.getText().toString().trim().toLowerCase();
        String meaning = inputMeaning.getText().toString().trim().toLowerCase();

        if(word.isEmpty() || meaning.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.input_error_title);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setMessage(R.string.wrong_word);
            builder.show();
        } else {
            dictionary.addWord(word, meaning);

            Snackbar.make(inputWord, R.string.word_added, Snackbar.LENGTH_LONG).show();

            inputWord.setText("");
            inputMeaning.setText("");
        }

    }
    private void initDictionary() {
        dictionary = (Dictionary) SingletonMap.getInstance().get(MainActivity.SHARED_AGENDA);
        if(dictionary == null) {
            dictionary = new Dictionary(getApplicationContext(), MainActivity.DATABASE_NAME);
            SingletonMap.getInstance().put(MainActivity.SHARED_AGENDA, dictionary);
        }
    }
}