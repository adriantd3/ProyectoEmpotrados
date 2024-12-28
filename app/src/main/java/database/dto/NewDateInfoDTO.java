package database.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NewDateInfoDTO {
    private Integer DestinyId;
    private LocalDate date;
    private Float tmp;
    private Integer weatherCode;
}
