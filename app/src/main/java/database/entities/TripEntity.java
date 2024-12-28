package database.entities;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TripEntity {
    private Integer id;
    private String name;
    private LocalDate initDate;
    private LocalDate endDate;
    private Integer NDestinies;
    private Float minTmp;
    private Float avgTmp;
    private Float maxTmp;
}
