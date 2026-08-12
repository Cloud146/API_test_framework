package tests;

import Specification_lib.RequestDataBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import tests.base.ApiBaseTest;

import static TestUtil_lib.ResponseAssert.assertThat;

public class TestCase extends ApiBaseTest {

    @Test(testName = "Тест запроса GET", groups = "API_base_tests")
    public void test_1(){
        Response response = getRequest.get(env().get("base_url").asText() +"/posts/1");

        assertThat(response)
                .hasStatusCode(200);
    }

    @Test(testName = "Тест запроса GET с BasicAuth", groups = "API_base_tests")
    public void test_2(){
        RequestSpecification reqSpec = RequestDataBuilder.request()
                .setContentType(ContentType.JSON)
                .setBasicAuth(testData().get("username").asText(), testData().get("password").asText())
                .build("BasicAuth для админа");
        Response response = getRequest.get(env().get("base_url").asText() +"/users/me", reqSpec);

        assertThat(response)
                .hasStatusCode(200);
    }

    @Test(testName = "Тест запроса POST", groups = "API_base_tests")
    public void test_3(){
        RequestSpecification adminSpec = RequestDataBuilder.request()
                .setContentType(ContentType.JSON)
                .setBasicAuth(testData().get("username").asText(), testData().get("password").asText())
                .setBody("payloads/create_post.json")
                .build("BasicAuth для админа");
        Response response = postRequest.post(env().get("base_url").asText() +"/posts", adminSpec);

        assertThat(response)
                .hasStatusCode(201);
    }
}
