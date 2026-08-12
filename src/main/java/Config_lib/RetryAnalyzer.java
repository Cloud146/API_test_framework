package Config_lib;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        int maxRetries = ConfigProvider.get().getRunnerSettings().getRetryCounts();

        if (count < maxRetries) {
            count++;

            org.testng.annotations.Test testAnnotation = result.getMethod()
                    .getConstructorOrMethod().getMethod().getAnnotation(org.testng.annotations.Test.class);

            String testName = (testAnnotation != null && !testAnnotation.testName().isEmpty())
                    ? testAnnotation.testName()
                    : result.getMethod().getMethodName();

            Throwable error = result.getThrowable();
            String errorMessage = (error != null) ? error.getMessage() : "Неизвестная ошибка";

            log.warn("Тест '{}' упал. Причина: {}. Запускаем ретрай: попытка {} из {}",
                    testName, errorMessage, count, maxRetries);

            return true;
        }

        return false;
    }
}