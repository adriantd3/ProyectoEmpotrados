package ssedm.lcc.example.newdictionarywithddbb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class Dictionary {

    private SQLiteDatabase db;

    public Dictionary(Context context, String dbName) {
        DictDbHelper dbHelper = new DictDbHelper(context, dbName);
        db = dbHelper.getWritableDatabase();
    }

    public void initDict() {
        // Adición de valores a la BD
        ContentValues values = new ContentValues();
        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "nombre");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "name");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);

        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "significado");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "meaning");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);

        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "palabra");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "word");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);

        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "hola");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "hello");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);

        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "adios");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "bye");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);

        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, "playa");
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, "beach");
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public String searchMeaning(String word) {
        String meaning ="";

        String[] columns = {
                DictContract.DictEntry._ID,
                DictContract.DictEntry.COLUMN_NAME_KEY,
                DictContract.DictEntry.COLUMN_NAME_VAL
        };
        String where = DictContract.DictEntry.COLUMN_NAME_KEY + " = ?";
        String[] whereArgs = { word };
        Cursor cursor = db.query(DictContract.DictEntry.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            while (cursor.moveToNext()) {
                meaning = cursor.getString(cursor.getColumnIndex(DictContract.DictEntry.COLUMN_NAME_VAL));
            }
        } finally {
            cursor.close();
        }
        return meaning;
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }

    public void addWord(String word, String meaning) {
        ContentValues values = new ContentValues();
        values.put(DictContract.DictEntry.COLUMN_NAME_KEY, word);
        values.put(DictContract.DictEntry.COLUMN_NAME_VAL, meaning);
        db.insert(DictContract.DictEntry.TABLE_NAME, null, values);
    }
}
