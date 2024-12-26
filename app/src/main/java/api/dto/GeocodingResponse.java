package api.dto;

import lombok.Data;

@Data
public class GeocodingResponse {
    private String direction;
    private Float lat;
    private Float lon;
}
