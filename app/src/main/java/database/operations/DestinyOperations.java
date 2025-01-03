package database.operations;

import static database.tables.DestinyTable.COLUMN_NAME_ARRIVAL_DATE;
import static database.tables.DestinyTable.COLUMN_NAME_DEPARTURE_DATE;
import static database.tables.DestinyTable.COLUMN_NAME_ID;
import static database.tables.DestinyTable.COLUMN_NAME_LAT;
import static database.tables.DestinyTable.COLUMN_NAME_LON;
import static database.tables.DestinyTable.COLUMN_NAME_TRIP_ID;
import static database.tables.DestinyTable.COLUMN_NAME_NAME;
import static database.tables.DestinyTable.TABLE_NAME;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import api.nominatim.GeocodingResponse;
import database.entities.DateInfoEntity;
import database.entities.DestinyEntity;
import database.dto.NewDestinyDTO;

public class DestinyOperations {

    private DateInfoOperations dateInfoOperations = new DateInfoOperations();
    public List<DestinyEntity> getAllByTripId(SQLiteDatabase db, Integer tripId) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_TRIP_ID + " = ?",
                new String[]{tripId.toString()},
                null,
                null,
                null
        );
        List<DestinyEntity> entities = new ArrayList<>();
        while (cursor.moveToNext()) {
            DestinyEntity entity = mapCursorToEntity(cursor);
            entity.setDateInfo(dateInfoOperations.getAllDateInfoByDestinyId(db, entity.getId()));
            entities.add(entity);
        }
        cursor.close();

        return entities;
    }

    public DestinyEntity getDestinyById(SQLiteDatabase db, Integer id) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_ID + " = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );
        DestinyEntity entity = null;
        if (cursor.moveToFirst()) {
            entity = mapCursorToEntity(cursor);
            entity.setDateInfo(dateInfoOperations.getAllDateInfoByDestinyId(db, entity.getId()));
        }
        cursor.close();
        return entity;
    }

    public Integer insertDestiny(SQLiteDatabase db, NewDestinyDTO newDestinyDTO, GeocodingResponse response) {
        return (int) db.insert(TABLE_NAME, null, mapEntitiesToContentValues(newDestinyDTO, response));
    }

    public void updateDestiny(SQLiteDatabase db, DestinyEntity destiny, GeocodingResponse geocoding) {
        db.update(TABLE_NAME, mapEntityToContentValues(destiny,geocoding), COLUMN_NAME_ID + " = ?", new String[]{destiny.getId().toString()});
    }

    public void deleteDestiny(SQLiteDatabase db, Integer id) {
        db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{id.toString()});
    }

    private DestinyEntity mapCursorToEntity(Cursor cursor) {
        DestinyEntity entity = new DestinyEntity();
        entity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
        entity.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NAME)));
        entity.setTripId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_TRIP_ID)));
        entity.setLat(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_NAME_LAT)));
        entity.setLon(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_NAME_LON)));
        entity.setArrivalDate(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ARRIVAL_DATE))));
        entity.setDepartureDate(LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DEPARTURE_DATE))));
        return entity;
    }

    /**
     * It it used for updates. It may update the name, so it is necessary to consider the geocoding response
     * If the geocoding is null, it means that the name was not updated
     */
    private ContentValues mapEntityToContentValues(DestinyEntity destiny, GeocodingResponse geocoding) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, destiny.getId());
        values.put(COLUMN_NAME_TRIP_ID, destiny.getTripId());
        values.put(COLUMN_NAME_NAME, destiny.getName());
        values.put(COLUMN_NAME_LAT, geocoding == null ? destiny.getLat() : geocoding.getLat());
        values.put(COLUMN_NAME_LON, geocoding == null ? destiny.getLon() : geocoding.getLon());
        values.put(COLUMN_NAME_ARRIVAL_DATE, destiny.getArrivalDate().toString());
        values.put(COLUMN_NAME_DEPARTURE_DATE, destiny.getDepartureDate().toString());
        return values;
    }

    private ContentValues mapEntitiesToContentValues(NewDestinyDTO destiny, GeocodingResponse geocoding) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, destiny.getName());
        values.put(COLUMN_NAME_TRIP_ID, destiny.getTripId());
        values.put(COLUMN_NAME_LAT, geocoding.getLat());
        values.put(COLUMN_NAME_LON, geocoding.getLon());
        values.put(COLUMN_NAME_ARRIVAL_DATE, destiny.getArrivalDate().toString());
        values.put(COLUMN_NAME_DEPARTURE_DATE, destiny.getDepartureDate().toString());
        return values;
    }
}
