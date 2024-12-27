package api.tomorrowio.response;

import java.sql.Date;

import lombok.Data;

@Data
public class IntervalObject {
    private Date startTime;
    private ValueObject values;
}
