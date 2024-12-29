package api.tomorrowio;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TimelinesBody {
    @SerializedName("location")
    private String location;

    @SerializedName("fields")
    private String[] fields;

    @SerializedName("timesteps")
    private String[] timesteps;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;
}
