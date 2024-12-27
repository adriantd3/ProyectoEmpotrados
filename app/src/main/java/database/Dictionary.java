package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import api.nominatim.GeocodingResponse;
import database.dto.NewDestinyDTO;
import database.dto.NewTripDTO;
import database.operations.DateInfoOperations;
import database.operations.DestinyOperations;
import database.operations.TripOperations;
import database.tables.InterfaceTable;
import database.tables.DateInfoTable;
import database.tables.DestinyTable;
import database.tables.TripTable;

import database.entities.*;

public class Dictionary {
    private SQLiteDatabase db;
    private TripOperations tripOperations;
    private DestinyOperations destinyOperations;
    private DateInfoOperations dateInfoOperations;

    public Dictionary(Context context) {
        List<InterfaceTable> tables = new ArrayList<>();
        tables.add(new TripTable());
        tables.add(new DestinyTable());
        tables.add(new DateInfoTable());
        DictDbHelper dbHelper = new DictDbHelper(context, tables);
        this.db = dbHelper.getWritableDatabase();

        this.tripOperations = new TripOperations();
        this.destinyOperations = new DestinyOperations();
        this.dateInfoOperations = new DateInfoOperations();
    }

    // TripEntity methods
    public TripEntity getTripById(Integer id) {
        return tripOperations.getTripById(db, id);
    }

    public List<TripEntity> getAllTrips() {
        return tripOperations.getAllTrips(db);
    }

    public void insertTrip(NewTripDTO trip) {
        tripOperations.insertTrip(db, trip);
    }

    public void updateTrip(TripEntity trip) {
        tripOperations.updateTrip(db, trip);
    }

    public void deleteTrip(Integer id) {
        tripOperations.deleteTrip(db, id);
    }

    // DestinyEntity methods
    public List<DestinyEntity> getAllByTripId(Integer tripId) {
        return destinyOperations.getAllByTripId(db, tripId);
    }

    public DestinyEntity getDestinyById(Integer id) {
        return destinyOperations.getDestinyById(db, id);
    }

    public void insertDestiny(NewDestinyDTO destiny, GeocodingResponse response) {
        destinyOperations.insertDestiny(db, destiny, response);
    }

    public void updateDestiny(DestinyEntity destiny, GeocodingResponse geocoding) {
        destinyOperations.updateDestiny(db, destiny, geocoding);
    }

    public void deleteDestiny(Integer id) {
        destinyOperations.deleteDestiny(db, id);
    }

    // DateInfoEntity methods
    public List<DateInfoEntity> getDateInfoByDestinyId(Integer destinyId) {
        return dateInfoOperations.getDateInfoByDestinyId(db, destinyId);
    }

    public DateInfoEntity getDateInfoById(Integer id) {
        return dateInfoOperations.getDateInfoById(db, id);
    }

    public void insertDateInfo(DateInfoEntity dateInfo) {
        dateInfoOperations.insertDateInfo(db, dateInfo);
    }

    public void updateDateInfo(DateInfoEntity dateInfo) {
        dateInfoOperations.updateDateInfo(db, dateInfo);
    }

    public void deleteDateInfo(Integer id) {
        dateInfoOperations.deleteDateInfo(db, id);
    }
}
