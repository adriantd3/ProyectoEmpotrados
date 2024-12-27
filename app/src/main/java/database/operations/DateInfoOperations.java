package database.operations;

import static database.tables.DateInfoTable.COLUMN_NAME_DATE;
import static database.tables.DateInfoTable.COLUMN_NAME_DESTINY_ID;
import static database.tables.DateInfoTable.COLUMN_NAME_ID;
import static database.tables.DateInfoTable.COLUMN_NAME_TMP;
import static database.tables.DateInfoTable.COLUMN_NAME_WEATHER_CODE;
import static database.tables.DateInfoTable.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import database.entities.DateInfoEntity;

public class DateInfoOperations {

    public List<DateInfoEntity> getDateInfoByDestinyId(SQLiteDatabase db, Integer destinyId) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_DESTINY_ID + " = ?",
                new String[]{destinyId.toString()},
                null,
                null,
                null
        );

        List<DateInfoEntity> entities = new ArrayList<>();
        while (cursor.moveToNext()) {
            entities.add(mapCursorToEntity(cursor));
        }
        cursor.close();
        return entities;
    }

    public DateInfoEntity getDateInfoById(SQLiteDatabase db, Integer id) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_ID + " = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );
        DateInfoEntity entity = null;
        if (cursor.moveToFirst()) {
            entity = mapCursorToEntity(cursor);
        }
        cursor.close();
        return entity;
    }

    public void insertDateInfo(SQLiteDatabase db, DateInfoEntity dateInfo) {
        db.insert(TABLE_NAME, null, mapEntityToContentValues(dateInfo));
    }

    public void updateDateInfo(SQLiteDatabase db, DateInfoEntity dateInfo) {
        db.update(TABLE_NAME, mapEntityToContentValues(dateInfo), COLUMN_NAME_ID + " = ?", new String[]{dateInfo.getId().toString()});
    }

    public void deleteDateInfo(SQLiteDatabase db, Integer id) {
        db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{id.toString()});
    }

    private DateInfoEntity mapCursorToEntity(Cursor cursor) {
        DateInfoEntity entity = new DateInfoEntity();
        entity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
        entity.setDestinyId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESTINY_ID)));
        entity.setDate(Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DATE))));
        entity.setTmp(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_NAME_TMP)));
        entity.setWeatherCode(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_WEATHER_CODE)));
        return entity;
    }

    private ContentValues mapEntityToContentValues(DateInfoEntity dateInfo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DESTINY_ID, dateInfo.getDestinyId());
        values.put(COLUMN_NAME_DATE, dateInfo.getDate().toString());
        values.put(COLUMN_NAME_TMP, dateInfo.getTmp());
        values.put(COLUMN_NAME_WEATHER_CODE, dateInfo.getWeatherCode());
        return values;
    }
}
