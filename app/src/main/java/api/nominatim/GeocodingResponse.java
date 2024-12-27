package api.nominatim;

import lombok.Data;

@Data
public class GeocodingResponse {
    private Float lat;
    private Float lon;
}
