package Requests_lib;

import Specification_lib.FrameworkSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class PutRequest {

    @Step("Отправить PUT запрос на URL: {url}")
    public Response put(String url) {
        log.info("Отправлен PUT запрос на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .put(url);
    }

    @Step("Отправить PUT запрос со спецификацией на URL: {url}")
    public Response put(String url, RequestSpecification customApiSpec) {
        log.info("Отправлен PUT запрос со спецификацией на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .spec(customApiSpec)
                .put(url);
    }
}