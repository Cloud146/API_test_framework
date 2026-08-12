package Data_lib;

import Config_lib.ConfigProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.Reporter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class TestDataHelper {

    // Кэш для хранения всех тестовых данных: Ключ = testName, Значение = блок данных
    private static final Map<String, JsonNode> testDataCache = new HashMap<>();
    private static boolean isInitialized = false;

    private static synchronized void init() {
        if (isInitialized) return;

        String testDataDirPath = ConfigProvider.get().getPaths().getTestDataDir();
        Path startPath = Paths.get(testDataDirPath);

        if (!Files.exists(startPath)) {
            log.error("Папка с тестовыми данными не найдена: {}", startPath.toAbsolutePath());
            throw new RuntimeException("Не найдена папка: " + testDataDirPath);
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".yml") || p.toString().endsWith(".yaml"))
                    .forEach(file -> {
                        try {
                            JsonNode rootNode = mapper.readTree(file.toFile());

                            if (rootNode != null && rootNode.isArray()) {
                                for (JsonNode node : rootNode) {
                                    JsonNode testNameNode = node.get("TestName");

                                    if (testNameNode != null && testNameNode.isTextual()) {
                                        String testName = testNameNode.asText();

                                        if (testDataCache.containsKey(testName)) {
                                            log.error("КРИТИЧЕСКАЯ ОШИБКА: Найден дубликат данных для теста '{}' в файле {}", testName, file.getFileName());
                                            throw new RuntimeException("Дублирование TestName в тестовых данных: " + testName);
                                        }
                                        testDataCache.put(testName, node);
                                    } else {
                                        log.warn("В файле {} найден блок без текстового поля 'TestName'. Пропускаем его.", file.getFileName());
                                    }
                                }
                            } else {
                                log.warn("Файл {} имеет неверный формат (ожидается массив с '-'). Пропускаем.", file.getFileName());
                            }
                        } catch (IOException e) {
                            log.error("Ошибка при чтении файла {}: {}", file.getFileName(), e.getMessage());
                        }
                    });

            isInitialized = true;
            log.info("Успешно загружены тестовые данные для {} уникальных тестов: {}", testDataCache.size(), testDataCache.keySet());

        } catch (IOException e) {
            log.error("Ошибка при сканировании папки {}: {}", testDataDirPath, e.getMessage());
            throw new RuntimeException("Не удалось загрузить тестовые данные", e);
        }
    }

    /**
     * Метод определяет, какой тест сейчас идет,
     * и отдает нужные данные из памяти.
     */
    public static JsonNode getCurrentTestData() {
        if (!isInitialized) init();

        ITestResult result = Reporter.getCurrentTestResult();
        if (result == null) {
            throw new RuntimeException("Метод getCurrentTestData() можно вызывать только внутри TestNG теста!");
        }

        org.testng.annotations.Test testAnnotation = result.getMethod()
                .getConstructorOrMethod().getMethod().getAnnotation(org.testng.annotations.Test.class);

        String testName = (testAnnotation != null && !testAnnotation.testName().isEmpty())
                ? testAnnotation.testName()
                : result.getMethod().getMethodName();

        if (!testDataCache.containsKey(testName)) {
            throw new RuntimeException("В папке тестовых данных не найден блок для теста: '" + testName + "'");
        }

        return testDataCache.get(testName);
    }
}