package api.tomorrowio.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TimelineObject {
    private String timestep;
    private LocalDate endTime;
    private LocalDate startTime;
    private List<IntervalObject> intervals;
}
