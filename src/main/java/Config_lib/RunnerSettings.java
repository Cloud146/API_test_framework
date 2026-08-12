package Config_lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class RunnerSettings {
    private boolean parallel;
    private int threads;

    @JsonProperty("retry_counts")
    private int retryCounts;

    @JsonProperty("run_by")
    private String runBy;

    private List<String> value;
}