package Config_lib;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ConfigProvider {

    private static FrameworkConfig config;

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (InputStream is = ConfigProvider.class.getClassLoader().getResourceAsStream("framework-config.yml")) {
            if (is == null) {
                throw new RuntimeException("Файл framework-config.yml не найден в resources!");
            }
            java.io.Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            config = mapper.readValue(reader, FrameworkConfig.class);

            String logLevelStr = "INFO";
            if (config.getApiSettings() != null && config.getApiSettings().getLogLevel() != null) {
                logLevelStr = config.getApiSettings().getLogLevel();
            }

            Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            rootLogger.setLevel(Level.toLevel(logLevelStr.toUpperCase()));

            log.trace("YAML конфигурация успешно загружена. Уровень логов установлен: {}", logLevelStr);
        } catch (Exception e) {
            log.error("Критическая ошибка при чтении framework-config.yml: {}", e.getMessage());
            throw new RuntimeException("Не удалось загрузить конфигурацию", e);
        }
    }

    public static FrameworkConfig get() {
        return config;
    }
}