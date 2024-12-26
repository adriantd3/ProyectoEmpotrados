package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.tables.InterfaceTable;
import database.tables.DateInfoTable;
import database.tables.DestinyTable;
import database.tables.TripTable;

public class Dictionary {
    private SQLiteDatabase db;

    public Dictionary(Context context, String dbName) {
        List<InterfaceTable> tables = new ArrayList<>();
        tables.add(new TripTable());
        tables.add(new DestinyTable());
        tables.add(new DateInfoTable());
        DictDbHelper dbHelper = new DictDbHelper(context, tables);
        this.db = dbHelper.getWritableDatabase();
    }
}
