package database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DateInfoTable implements InterfaceTable {

    public static final String TABLE_NAME = "DateInfo";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_DESTINY_ID = "destiny_id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TMP = "tmp";
    public static final String COLUMN_NAME_WEATHER_CODE = "weather_code";

    @Override
    public String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_DESTINY_ID + " INTEGER," +
                COLUMN_NAME_DATE + " TEXT," +
                COLUMN_NAME_TMP + " REAL," +
                COLUMN_NAME_WEATHER_CODE + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_NAME_DESTINY_ID + ") REFERENCES " + DestinyTable.TABLE_NAME + "(" + DestinyTable.COLUMN_NAME_ID + ") ON DELETE CASCADE ON UPDATE CASCADE)";
    }

    @Override
    public String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void addInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DESTINY_ID, 1);
        values.put(COLUMN_NAME_DATE, "2024-12-29");
        values.put(COLUMN_NAME_TMP, 12.0);
        values.put(COLUMN_NAME_WEATHER_CODE, 1000);

        db.insert(TABLE_NAME, null, values);

        values.put(COLUMN_NAME_DATE, "2024-12-30");
        values.put(COLUMN_NAME_TMP, 16.0);
        values.put(COLUMN_NAME_WEATHER_CODE, 1001);

        db.insert(TABLE_NAME, null, values);
    }
}
