package Config_lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FrameworkConfig {

    @JsonProperty("runner_settings")
    private RunnerSettings runnerSettings;

    @JsonProperty("api_settings")
    private ApiSettings apiSettings;

    @JsonProperty("paths")
    private PathsConfig paths;
}