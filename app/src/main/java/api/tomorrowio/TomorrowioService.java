package api.tomorrowio;

import java.sql.Date;

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

    public void getWeatherData(GeocodingResponse geocodingResponse, Date startTime, Date endTime, Callback<TomorrowResponse> callback) {
        String location = geocodingResponse.getLat() + ", " + geocodingResponse.getLon();
        String[] fields = {"temperature", "weatherCode"};
        String[] timesteps = {"1h"};

        Call<TomorrowResponse> call = api.getWeatherData(location, fields, timesteps, startTime, endTime);
        call.enqueue(callback);
    }
}