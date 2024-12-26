package database.entities;

import java.sql.Date;

import lombok.Data;

@Data
public class NewTrip {
    public String name;
    public Date initDate;
    public Date endDate;
}
