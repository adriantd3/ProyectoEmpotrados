package database.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NewDestinyDTO {
    // El resto de la informacion la proporciona la API de geocoding
    private String name;
    private Integer TripId;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
}
