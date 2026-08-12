package Specification_lib;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;

public class FrameworkSpecs {

    public static RequestSpecification getFrameworkSpec() {
        return new RequestSpecBuilder()

                .addFilter(new AllureRestAssured())

                .setConfig(RestAssuredConfig.config()

                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 15000)
                                .setParam("http.socket.timeout", 15000)
                        )

                        .sslConfig(new SSLConfig().relaxedHTTPSValidation())
                )

                .build();
    }
}
