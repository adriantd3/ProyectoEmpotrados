package database.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewTripDTO {
    @NonNull
    public String name;
}
