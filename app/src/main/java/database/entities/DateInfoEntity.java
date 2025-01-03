package database.entities;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DateInfoEntity {
    private Integer id;
    private Integer DestinyId;
    private LocalDate date;
    private Float tmp;
    private Integer weatherCode;
}
