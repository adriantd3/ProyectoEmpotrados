package database.operations;

import static database.tables.TripTable.COLUMN_NAME_AVG_TMP;
import static database.tables.TripTable.COLUMN_NAME_END_DATE;
import static database.tables.TripTable.COLUMN_NAME_ID;
import static database.tables.TripTable.COLUMN_NAME_INIT_DATE;
import static database.tables.TripTable.COLUMN_NAME_MAX_TMP;
import static database.tables.TripTable.COLUMN_NAME_MIN_TMP;
import static database.tables.TripTable.COLUMN_NAME_NAME;
import static database.tables.TripTable.COLUMN_NAME_NDESTINIES;
import static database.tables.TripTable.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.dto.NewTripDTO;
import database.entities.TripEntity;

public class TripOperations {
    public TripEntity getTripById(SQLiteDatabase db, Integer id) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_ID + " = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );
        TripEntity entity = null;
        if (cursor.moveToFirst()) {
            entity = mapCursorToEntity(cursor);
        }
        cursor.close();
        return entity;
    }

    public List<TripEntity> getAllTrips(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<TripEntity> entities = new ArrayList<>();
        while (cursor.moveToNext()) {
            entities.add(mapCursorToEntity(cursor));
        }
        cursor.close();
        return entities;
    }

    public void insertTrip(SQLiteDatabase db, NewTripDTO trip) {
        db.insert(TABLE_NAME, null, mapEntityToContentValues(trip));
    }

    public void updateTrip(SQLiteDatabase db, TripEntity trip) {
        db.update(TABLE_NAME, mapEntityToContentValues(trip), COLUMN_NAME_ID + " = ?", new String[]{trip.getId().toString()});
    }

    public void deleteTrip(SQLiteDatabase db, Integer id) {
        db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{id.toString()});
    }

    private TripEntity mapCursorToEntity(Cursor cursor) {
        TripEntity entity = new TripEntity();
        entity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
        entity.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NAME)));

        String initDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_INIT_DATE));
        String endDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_END_DATE));

        if (initDateString != null && !initDateString.isEmpty()) {
            try {
                entity.setInitDate(LocalDate.parse(initDateString));
            } catch (Exception e) {
                //Log.e(TAG, "Error parsing init date: " + initDateString, e);
                entity.setInitDate(null);
            }
        } else {
            entity.setInitDate(null);
        }

        if (endDateString != null && !endDateString.isEmpty()) {
            try {
                entity.setEndDate(LocalDate.parse(endDateString));
            } catch (Exception e) {
                //Log.e(TAG, "Error parsing end date: " + endDateString, e);
                entity.setEndDate(null);
            }
        } else {
            entity.setEndDate(null);
        }

        entity.setNDestinies(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_NDESTINIES)));

        return entity;
    }

    private ContentValues mapEntityToContentValues(TripEntity trip) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, trip.getName());
        values.put(COLUMN_NAME_INIT_DATE, trip.getInitDate().toString());
        values.put(COLUMN_NAME_END_DATE, trip.getEndDate().toString());
        values.put(COLUMN_NAME_NDESTINIES, trip.getNDestinies());
        values.put(COLUMN_NAME_MIN_TMP, trip.getMinTmp());
        values.put(COLUMN_NAME_AVG_TMP, trip.getAvgTmp());
        values.put(COLUMN_NAME_MAX_TMP, trip.getMaxTmp());
        return values;
    }

    private ContentValues mapEntityToContentValues(NewTripDTO trip) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, trip.getName());
        values.put(COLUMN_NAME_NDESTINIES, 0);
        return values;
    }
}
