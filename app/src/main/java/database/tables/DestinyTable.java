package database.tables;

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
                COLUMN_NAME_LAT + " REAL," +
                COLUMN_NAME_LON + " REAL," +
                COLUMN_NAME_ARRIVAL_DATE + " DATE," +
                COLUMN_NAME_DEPARTURE_DATE + " DATE" +
                " ) " +
                "FOREIGN KEY(" + COLUMN_NAME_TRIP_ID + ") REFERENCES " + TripTable.TABLE_NAME + "(" + TripTable.COLUMN_NAME_ID + ") ON DELETE CASCADE ON UPDATE CASCADE";
    }

    @Override
    public String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void addInitialData(SQLiteDatabase db) {

    }
}
