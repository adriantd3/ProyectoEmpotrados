package api.tomorrowio;

import api.tomorrowio.response.TomorrowResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TomorrowioAPI {
    static final String API_KEY = "FXYCFZZIY5iVWuxpINvAw90mdRAMaIOM";
    @POST("timelines?apikey=" + API_KEY )
    Call<TomorrowResponse> getWeatherData(
            @Body TimelinesBody body
    );
}
