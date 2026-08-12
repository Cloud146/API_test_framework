package Requests_lib;

import Specification_lib.FrameworkSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class PatchRequest {

    @Step("Отправить PATCH запрос на URL: {url}")
    public Response patch(String url) {
        log.info("Отправлен PATCH запрос на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .patch(url);
    }

    @Step("Отправить PATCH запрос со спецификацией на URL: {url}")
    public Response patch(String url, RequestSpecification customApiSpec) {
        log.info("Отправлен PATCH запрос со спецификацией на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .spec(customApiSpec)
                .patch(url);
    }
}