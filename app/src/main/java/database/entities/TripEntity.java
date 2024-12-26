package database.entities;
import java.sql.Date;

import lombok.Data;

@Data
public class TripEntity {
    private Integer id;
    private String name;
    private Date initDate;
    private Date endDate;
    private Integer NDestinies;
    private Float minTmp;
    private Float avgTmp;
    private Float maxTmp;
}
