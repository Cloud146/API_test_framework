package TestUtil_lib;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

    private ResponseAssert(Response actual) {
        super(actual, ResponseAssert.class);
    }

    public static ResponseAssert assertThat(Response actual) {
        return new ResponseAssert(actual);
    }

    @Step("Проверить статус код ответа")
    public ResponseAssert hasStatusCode(int expectedStatusCode) {
        isNotNull();
        int actualStatusCode = actual.getStatusCode();
        Assertions.assertThat(actualStatusCode)
                .withFailMessage("Ожидаемый статус код <%s> не соответствует реальному <%s>", expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
        return this;
    }
}
