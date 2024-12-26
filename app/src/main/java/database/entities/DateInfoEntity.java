package database.entities;

import java.sql.Date;

import lombok.Data;

@Data
public class DateInfoEntity {
    private Integer id;
    private Integer DestinyId;
    private Date date;
    private Float tmp;
    private Integer weatherCode;
}
