package Data_lib;

import Config_lib.ConfigProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class EnvDataHelper {

    private static JsonNode envData;

    static {
        String envDirPath = ConfigProvider.get().getPaths().getEnvSettingsDir();
        Path envFilePath = Paths.get(envDirPath, "stand_data.yml");

        if (!Files.exists(envFilePath)) {
            log.error("Файл настроек стенда не найден: {}", envFilePath.toAbsolutePath());
            throw new RuntimeException("Не найден файл: " + envFilePath);
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream is = Files.newInputStream(envFilePath)) {
            envData = mapper.readTree(is);
            log.info("Общие данные стенда успешно загружены из {}", envFilePath.getFileName());
        } catch (Exception e) {
            log.error("Ошибка при чтении файла {}: {}", envFilePath.getFileName(), e.getMessage());
            throw new RuntimeException("Не удалось загрузить настройки стенда", e);
        }
    }

    public static JsonNode get() {
        return envData;
    }
}