package api.nominatim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NominatimService{
    private static final String BASE_URL = "https://nominatim.openstreetmap.org/";
    private NominatimAPI api;

    public NominatimService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(NominatimAPI.class);
    }

    public void getCoordinates(String address, Callback<List<GeocodingResponse>> callback) {
        Call<List<GeocodingResponse>> call = api.getCoordinates(address);
        call.enqueue(callback);
        // Se implementa el resultado de la funcion en la actividad
    }

}
