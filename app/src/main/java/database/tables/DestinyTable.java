package database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DestinyTable implements InterfaceTable {

    public static final String TABLE_NAME = "Destiny";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TRIP_ID = "trip_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_LAT = "lat";
    public static final String COLUMN_NAME_LON = "lon";
    public static final String COLUMN_NAME_ARRIVAL_DATE = "arrival_date";
    public static final String COLUMN_NAME_DEPARTURE_DATE = "departure_date";

    @Override
    public String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TRIP_ID + " INTEGER," +
                COLUMN_NAME_NAME + " TEXT," +
                COLUMN_NAME_LAT + " REAL," +
                COLUMN_NAME_LON + " REAL," +
                COLUMN_NAME_ARRIVAL_DATE + " TEXT," +
                COLUMN_NAME_DEPARTURE_DATE + " TEXT,  " +
                "FOREIGN KEY(" + COLUMN_NAME_TRIP_ID + ") REFERENCES " + TripTable.TABLE_NAME + "(" + TripTable.COLUMN_NAME_ID + ") ON DELETE CASCADE ON UPDATE CASCADE)";
    }

    @Override
    public String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void addInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TRIP_ID, 1);
        values.put(COLUMN_NAME_NAME, "London");
        values.put(COLUMN_NAME_LAT, 51.5074);
        values.put(COLUMN_NAME_LON, 0.1278);
        values.put(COLUMN_NAME_ARRIVAL_DATE, "2024-12-29");
        values.put(COLUMN_NAME_DEPARTURE_DATE, "2024-12-30");

        db.insert(TABLE_NAME, null, values);
    }
}
