package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.nominatim.GeocodingResponse;
import api.tomorrowio.response.IntervalObject;
import api.tomorrowio.response.TomorrowResponse;
import api.tomorrowio.response.ValueObject;
import database.dto.NewDateInfoDTO;
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
        recalculateTripValues(trip.getId());
    }

    public void deleteTrip(Integer id) {
        tripOperations.deleteTrip(db, id);
        for (DestinyEntity destiny : destinyOperations.getAllByTripId(db, id)){
            deleteDestiny(destiny.getId());
        }
    }

    // DestinyEntity methods
    public List<DestinyEntity> getAllByTripId(Integer tripId) {
        return destinyOperations.getAllByTripId(db, tripId);
    }

    public DestinyEntity getDestinyById(Integer id) {
        return destinyOperations.getDestinyById(db, id);
    }

    public void insertDestiny(NewDestinyDTO destiny, GeocodingResponse geocodingResponse, TomorrowResponse tomorrowResponse) {
        Integer destinyId = destinyOperations.insertDestiny(db, destiny, geocodingResponse);
        insertAllDateInfo(destinyId, tomorrowResponse);
        recalculateTripValues(destiny.getTripId());
    }

    public void updateDestiny(DestinyEntity destiny, GeocodingResponse geocoding, TomorrowResponse tomorrowResponse) {
        destinyOperations.updateDestiny(db, destiny, geocoding);

        deleteAllDestinyDateInfo(destiny);
        insertAllDateInfo(destiny.getId(), tomorrowResponse);
        recalculateTripValues(destiny.getTripId());
    }

    public void deleteDestiny(Integer id) {
        DestinyEntity destiny = destinyOperations.getDestinyById(db, id);
        TripEntity trip = tripOperations.getTripById(db, destiny.getTripId());
        deleteAllDestinyDateInfo(destiny);
        destinyOperations.deleteDestiny(db, id);
        updateTrip(trip);
    }

    // DateInfoEntity methods
    public List<DateInfoEntity> getDateInfoByDestinyId(Integer destinyId) {
        return dateInfoOperations.getAllDateInfoByDestinyId(db, destinyId);
    }

    private void insertAllDateInfo(Integer destinyId, TomorrowResponse tomorrowResponse){
        HashMap<LocalDate, List<ValueObject>> sortedIntervals = mapIntervalsByDate(tomorrowResponse);
        for (LocalDate date : sortedIntervals.keySet()) {
            float avgTmp = calculateAverageTemperature(sortedIntervals.get(date));
            int mostCommonWeatherCode = findMostCommonWeatherCode(sortedIntervals.get(date));

            NewDateInfoDTO dateInfo = new NewDateInfoDTO();
            dateInfo.setDestinyId(destinyId);
            dateInfo.setDate(date);
            dateInfo.setTmp(avgTmp);
            dateInfo.setWeatherCode(mostCommonWeatherCode);

            dateInfoOperations.insertDateInfo(db, dateInfo);
        }
    }

    private void recalculateTripValues(Integer tripId) {
        TripEntity trip = tripOperations.getTripById(db, tripId);
        List<DestinyEntity> destinyEntities = destinyOperations.getAllByTripId(db, tripId);

        LocalDate minDate = LocalDate.MAX, maxDate = LocalDate.MIN;
        float maxTmp = -1000, minTmp = 1000,  sumTmp = 0;
        int count = 0;
        for (DestinyEntity destinyEntity : destinyEntities) {
            for (DateInfoEntity dateInfoEntity : destinyEntity.getDateInfo()){
                if (dateInfoEntity.getDate().isBefore(minDate)) {
                    minDate = dateInfoEntity.getDate();
                }
                if (dateInfoEntity.getDate().isAfter(maxDate)) {
                    maxDate = dateInfoEntity.getDate();
                }
                if (dateInfoEntity.getTmp() > maxTmp) {
                    maxTmp = dateInfoEntity.getTmp();
                }
                if (dateInfoEntity.getTmp() < minTmp) {
                    minTmp = dateInfoEntity.getTmp();
                }
                sumTmp += dateInfoEntity.getTmp();
                count++;
            }
        }
        float avgTmp = sumTmp / count;

        trip.setInitDate(minDate);
        trip.setEndDate(maxDate);
        trip.setMinTmp(minTmp);
        trip.setMaxTmp(maxTmp);
        trip.setAvgTmp(avgTmp);
        trip.setNDestinies(destinyEntities.size());

        tripOperations.updateTrip(db, trip);
    }

    private HashMap<LocalDate, List<ValueObject>> mapIntervalsByDate(TomorrowResponse tomorrowResponse) {
        HashMap<LocalDate, List<ValueObject>> sortedIntervals = new HashMap<>();
        for (IntervalObject interval : tomorrowResponse.getData().getTimelines().get(0).getIntervals()) {
            LocalDate date = LocalDate.parse(interval.getStartTime(), DateTimeFormatter.ISO_DATE_TIME);
            sortedIntervals.computeIfAbsent(date, k -> new ArrayList<>()).add(interval.getValues());
        }
        return sortedIntervals;
    }

    private float calculateAverageTemperature(List<ValueObject> values) {
        double sumTmp = values.stream().mapToDouble(ValueObject::getTemperature).sum();
        return (float) sumTmp / values.size();
    }

    private int findMostCommonWeatherCode(List<ValueObject> values) {
        HashMap<Integer, Integer> weatherCodes = new HashMap<>();
        for (ValueObject value : values) {
            weatherCodes.merge(value.getWeatherCode(), 1, Integer::sum);
        }
        return weatherCodes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("No weather codes found"))
                .getKey();
    }

    private void deleteAllDestinyDateInfo(DestinyEntity destiny){
        for (DateInfoEntity dateInfoEntity : destiny.getDateInfo()){
            dateInfoOperations.deleteDateInfoById(db, dateInfoEntity.getId());
        }
        destiny.setDateInfo(new ArrayList<>());
    }
}
