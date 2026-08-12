package Config_lib;

import lombok.extern.slf4j.Slf4j;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestFilterListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        RunnerSettings settings = ConfigProvider.get().getRunnerSettings();

        String runBy = settings.getRunBy(); // "testName" или "group"
        List<String> targetValues = settings.getValue(); // Теперь мы сразу получаем список из YAML!

        if (targetValues == null || targetValues.isEmpty()) {
            log.info("В framework-config.yml список 'value' пустой. Запускаются все тесты.");
            return methods;
        }

        List<IMethodInstance> filteredMethods = new ArrayList<>();

        for (IMethodInstance methodInstance : methods) {
            boolean shouldRun = false;

            if ("testName".equalsIgnoreCase(runBy) || "scenario".equalsIgnoreCase(runBy)) {
                String methodName = methodInstance.getMethod().getMethodName();

                org.testng.annotations.Test testAnnotation = methodInstance.getMethod()
                        .getConstructorOrMethod().getMethod().getAnnotation(org.testng.annotations.Test.class);

                String annotationTestName = (testAnnotation != null) ? testAnnotation.testName() : "";

                log.debug("Анализ теста: метод='{}', testName='{}'. Ищем совпадения с: {}",
                        methodName, annotationTestName, targetValues);

                for (String target : targetValues) {
                    if (target.equalsIgnoreCase(methodName.trim()) ||
                            target.equalsIgnoreCase(annotationTestName.trim())) {
                        shouldRun = true;
                        break;
                    }
                }
            }
            else if ("group".equalsIgnoreCase(runBy)) {
                String[] methodGroups = methodInstance.getMethod().getGroups();
                if (methodGroups != null) {
                    for (String group : methodGroups) {
                        for (String target : targetValues) {
                            if (target.equalsIgnoreCase(group.trim())) {
                                shouldRun = true;
                                break;
                            }
                        }
                        if (shouldRun) break;
                    }
                }
            }

            if (shouldRun) {
                filteredMethods.add(methodInstance);
            }
        }

        log.debug("Фильтрация завершена. К запуску допущено: {} тестов", filteredMethods.size());
        return filteredMethods;
    }
}