package Utils;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class PayloadLoader {

    private PayloadLoader() {}

    public static String readString(String resourcePath) {
        try {
            return Files.readString(Paths.get(getResourceUrl(resourcePath).toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Не удалось прочитать текст из файла: " + resourcePath, e);
        }
    }

    public static File getFile(String resourcePath) {
        try {
            return new File(getResourceUrl(resourcePath).toURI());
        } catch (Exception e) {
            throw new RuntimeException("Не удалось найти файл: " + resourcePath, e);
        }
    }

    private static URL getResourceUrl(String path) {
        URL resource = PayloadLoader.class.getClassLoader().getResource(path);
        if (resource == null) throw new IllegalArgumentException("Файл не найден: " + path);
        return resource;
    }
}
