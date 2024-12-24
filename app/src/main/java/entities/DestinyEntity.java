package entities;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class DestinyEntity{
    private Integer id;
    private Integer TripId;
    private String name;
    private Float lat;
    private Float lon;
    private Date arrivalDate;
    private Date departureDate;
    private List<DateInfoEntity> dateInfo;
}
