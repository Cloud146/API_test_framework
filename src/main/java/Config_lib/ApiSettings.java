package Config_lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiSettings {

    @JsonProperty("timeout_ms")
    private int timeoutMs;

    @JsonProperty("log_all_requests")
    private boolean logAllRequests;

    @JsonProperty("log_level")
    private String logLevel;
}