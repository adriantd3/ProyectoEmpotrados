package database.tables;

import android.database.sqlite.SQLiteDatabase;

public class TripTable implements InterfaceTable {

    public static final String TABLE_NAME = "Trip";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_INIT_DATE = "init_date";
    public static final String COLUMN_NAME_END_DATE = "end_date";
    public static final String COLUMN_NAME_NDESTINIES = "n_destinies";
    public static final String COLUMN_NAME_MIN_TMP = "min_tmp";
    public static final String COLUMN_NAME_AVG_TMP = "avg_tmp";
    public static final String COLUMN_NAME_MAX_TMP = "max_tmp";

    @Override
    public String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NAME + " TEXT," +
                COLUMN_NAME_INIT_DATE + " DATE," +
                COLUMN_NAME_END_DATE + " DATE," +
                COLUMN_NAME_NDESTINIES + " INTEGER," +
                COLUMN_NAME_MIN_TMP + " FLOAT NULL," +
                COLUMN_NAME_AVG_TMP + " FLOAT NULL," +
                COLUMN_NAME_MAX_TMP + " FLOAT NULL" +
                " )";
    }

    @Override
    public String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void addInitialData(SQLiteDatabase db) {

    }
}
