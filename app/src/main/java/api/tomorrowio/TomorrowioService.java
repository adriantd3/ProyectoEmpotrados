package api.tomorrowio;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import api.nominatim.GeocodingResponse;
import api.tomorrowio.response.TomorrowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TomorrowioService {
    private static final String BASE_URL = "https://api.tomorrow.io/v4/";
    private TomorrowioAPI api;

    public TomorrowioService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(TomorrowioAPI.class);
    }

    public void getWeatherData(GeocodingResponse geocodingResponse, LocalDate startTime, LocalDate endTime, Callback<TomorrowResponse> callback) {
        String location = geocodingResponse.getLat() + "," + geocodingResponse.getLon();
        String[] fields = {"temperature", "weatherCode"};
        String[] timesteps = {"1h"};

        Instant startTimeInstant = startTime.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endTimeInstant = endTime.atTime(23, 59, 59).atZone(ZoneOffset.UTC).toInstant();

        TimelinesBody body = new TimelinesBody(location, fields, timesteps, startTimeInstant.toString(), endTimeInstant.toString());
        System.out.println(body);
        Call<TomorrowResponse> call = api.getWeatherData(body);
        call.enqueue(callback);
    }
}
