package api.tomorrowio.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ValueObject {
    private String temperature;
    private String weatherCode;
}
