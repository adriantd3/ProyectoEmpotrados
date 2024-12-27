package api.tomorrowio;

import java.sql.Date;

import api.tomorrowio.response.TomorrowResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TomorrowioAPI {
    static final String API_KEY = "FXYCFZZIY5iVWuxpINvAw90mdRAMaIOM";
    @POST("timelines?apikey=" + API_KEY )
    Call<TomorrowResponse> getWeatherData(
            @Query("location") String location,
            @Query("fields") String[] fields,
            @Query("timesteps") String[] timesteps,
            @Query("startTime") Date startTime,
            @Query("endTime") java.util.Date endTime
    );
}
