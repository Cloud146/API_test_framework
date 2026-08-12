package Requests_lib;

import Specification_lib.FrameworkSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class GetRequest {

    @Step("Отправить GET запрос URL: {url}")
    public Response get(String url) {
        log.info("Отправлен GET запрос на {}", url);
        log.debug("debugОтправлен GET запрос на {}", url);
        log.trace("traceОтправлен GET запрос на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .get(url);
    }

    @Step("Отправить GET запрос со спецификацией на URL: {url}")
    public Response get(String url, RequestSpecification customApiSpec) {
        log.info("Отправлен GET запрос со спецификацией на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .spec(customApiSpec)
                .get(url);
    }
}
