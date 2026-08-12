package Config_lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PathsConfig {

    @JsonProperty("env_settings_dir")
    private String envSettingsDir;

    @JsonProperty("mocks_dir")
    private String mocksDir;

    @JsonProperty("payloads_dir")
    private String payloadsDir;

    @JsonProperty("test_data_dir")
    private String testDataDir;
}