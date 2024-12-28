package database.entities;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DestinyEntity{
    private Integer id;
    private Integer TripId;
    private String name;
    private Float lat;
    private Float lon;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private List<DateInfoEntity> dateInfo;
}
