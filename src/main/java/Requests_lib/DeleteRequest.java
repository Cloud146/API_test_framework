package Requests_lib;

import Specification_lib.FrameworkSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.given;

@Slf4j
public class DeleteRequest {

    @Step("Отправить DELETE запрос на URL: {url}")
    public Response delete(String url) {
        log.info("Отправлен DELETE запрос на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .delete(url);
    }

    @Step("Отправить DELETE запрос со спецификацией на URL: {url}")
    public Response delete(String url, RequestSpecification customApiSpec) {
        log.info("Отправлен DELETE запрос со спецификацией на {}", url);
        return given()
                .spec(FrameworkSpecs.getFrameworkSpec())
                .spec(customApiSpec)
                .delete(url);
    }
}