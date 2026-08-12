package Requests_lib;

import Specification_lib.FrameworkSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class PostRequest {

    @Step("Отправить POST запрос на URL: {url}")
    public Response post(String url) {
        log.info("Отправлен POST запрос на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .post(url);
    }

    @Step("Отправить POST запрос со спецификацией на URL: {url}")
    public Response post(String url, RequestSpecification customApiSpec) {
        log.info("Отправлен POST запрос со спецификацией на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .spec(customApiSpec)
                .post(url);
    }
}
