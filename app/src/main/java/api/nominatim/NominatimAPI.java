package api.nominatim;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NominatimAPI {

    @Headers({
            "User-Agent: WeatherApp/1.0"
    })
    @GET("search?format=json&limit=1&addressdetails=1")
    Call<List<GeocodingResponse>> getCoordinates(
            @Query("q") String address
    );
}
