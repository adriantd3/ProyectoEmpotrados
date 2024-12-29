package api.tomorrowio.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class IntervalObject {
    private String startTime;
    private ValueObject values;
}
