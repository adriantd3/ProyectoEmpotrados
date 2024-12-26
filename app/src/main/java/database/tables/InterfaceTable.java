package database.tables;
import android.database.sqlite.SQLiteDatabase;

public interface InterfaceTable {
    String createTable();
    String deleteTable();
    void addInitialData(SQLiteDatabase db);
}
