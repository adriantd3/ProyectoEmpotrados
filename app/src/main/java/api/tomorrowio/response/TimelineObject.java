package api.tomorrowio.response;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class TimelineObject {
    private String timestep;
    private Date endTime;
    private Date startTime;
    private List<IntervalObject> intervals;
}
