package api.tomorrowio.response;

import java.util.List;

import lombok.Data;

@Data
public class DataObject {
    private List<TimelineObject> timelines;
}
