package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import database.tables.InterfaceTable;

public class DictDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Weatherio.db";
    private List<InterfaceTable> tables;
    public DictDbHelper(Context context, List<InterfaceTable> tables) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (InterfaceTable table : tables) {
            db.execSQL(table.createTable());
            table.addInitialData(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (InterfaceTable table : tables) {
            db.execSQL(table.deleteTable());
        }
        onCreate(db);
    }
}
