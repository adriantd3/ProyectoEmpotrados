package database.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class NewDestiny {
    // El resto de la informacion la proporciona la API de geocoding
    private Integer TripId;
    private Date arrivalDate;
    private Date departureDate;
}
