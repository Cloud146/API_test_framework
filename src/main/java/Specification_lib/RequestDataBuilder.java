package Specification_lib;

import Utils.PayloadLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Slf4j
public class RequestDataBuilder {

    private final RequestSpecBuilder builder;

    private RequestDataBuilder() {
        this.builder = new RequestSpecBuilder();
    }

    public static RequestDataBuilder request() {
        return new RequestDataBuilder();
    }

    //ТИП ДАННЫХ
    public RequestDataBuilder setContentType(ContentType contentType) {
        builder.setContentType(contentType);
        log.info("Добавлен Content-Type: {}", contentType);
        return this;
    }

    public RequestDataBuilder setAccept(ContentType acceptType) {
        builder.setAccept(acceptType);
        log.info("Добавлен Accept-Type: {}", acceptType);
        return this;
    }

    //АВТОРИЗАЦИЯ
    public RequestDataBuilder setBearerToken(String token) {
        builder.addHeader("Authorization", "Bearer " + token);
        log.info("Добавлен BearerToken: {}", token);
        return this;
    }

    public RequestDataBuilder setBasicAuth(String username, String password) {
        builder.setAuth(io.restassured.RestAssured.preemptive().basic(username, password));
        log.info("Добавлен BasicAuth c кредами: {} / {}", username, password);
        return this;
    }

    public RequestDataBuilder setApiKeyInHeader(String headerName, String apiKey) {
        builder.addHeader(headerName, apiKey);
        log.info("Добавлен ApiKey в заголовок: {}", apiKey);
        return this;
    }

    public RequestDataBuilder setApiKeyInQuery(String paramName, String apiKey) {
        builder.addQueryParam(paramName, apiKey);
        log.info("Добавлен ApiKey в параметре: {}", apiKey);
        return this;
    }

    public RequestDataBuilder setOAuth2(String accessToken) {
        builder.setAuth(io.restassured.RestAssured.oauth2(accessToken));
        log.info("Добавлен OAuth2 c токеном: {}", accessToken);
        return this;
    }

    public RequestDataBuilder setOAuth1(String consumerKey, String consumerSecret, String accessToken, String secretToken) {
        builder.setAuth(io.restassured.RestAssured.oauth(consumerKey, consumerSecret, accessToken, secretToken));
        log.info("Добавлен OAuth1 c кредами: {}, {}, {}, {}", consumerKey, consumerSecret, accessToken, secretToken);
        return this;
    }

    public RequestDataBuilder setCookieAuth(String cookieName, String cookieValue) {
        builder.addCookie(cookieName, cookieValue);
        log.info("Добавлен Cookie с авторизацией: {}", cookieName);
        return this;
    }

    public RequestDataBuilder setCookiesAuth(Map<String, String> cookies) {
        builder.addCookies(cookies);
        log.info("Добавлены Cookies с авторизацией: {}", cookies.toString());
        return this;
    }

    //ПАРАМЕТРЫ ЗАПРОСА
    public RequestDataBuilder addQueryParam(String key, Object value) {
        builder.addQueryParam(key, value);
        log.info("Добавлен параметр: {} = {}", key, value);
        return this;
    }

    public RequestDataBuilder addQueryParams(Map<String, ?> params) {
        builder.addQueryParams(params);
        log.info("Добавлены параметры: {}", params.toString());
        return this;
    }

    //ЗАГОЛОВКИ
    public RequestDataBuilder addHeader(String key, String value) {
        builder.addHeader(key, value);
        log.info("Добавлен заголовок: {} = {}", key, value);
        return this;
    }

    public RequestDataBuilder addHeaders(Map<String, String> headers) {
        builder.addHeaders(headers);
        log.info("Добавлены заголовки: {}", headers.toString());
        return this;
    }

    //ТЕЛО ЗАПРОСА
    public RequestDataBuilder setBody(Object body) {
        builder.setBody(body);
        log.info("Добавлено тело запроса: {}", body);
        return this;
    }

    public RequestDataBuilder setBody(String filePath) {
        if (filePath.matches(".*\\.(json|xml|txt|html)$")) {
            builder.setBody(PayloadLoader.readString(filePath));
            log.info("Добавлено тело запроса из файла: {}", filePath);
        }
        else {
            builder.addMultiPart(PayloadLoader.getFile(filePath));
            log.info("Добавлен файл (multipart) из: {}", filePath);
        }
        return this;
    }

    //ПРОКСИ
    public RequestDataBuilder setProxy(String host, int port) {
        builder.setProxy(host, port);
        log.info("Добавлен прокси. Хост: {}, порт: {} ", host, port);
        return this;
    }

    public RequestDataBuilder setProxy(String host, int port, String scheme, String username, String password) {
        builder.setProxy(
                io.restassured.specification.ProxySpecification.host(host)
                        .and().withPort(port)
                        .and().withScheme(scheme)
                        .and().withAuth(username, password)
        );
        log.info("Добавлен прокси с аутентификацией. Хост: {}, порт: {}. Схема: {}, пользователь: {}, пароль: {} ", host, port, scheme, username, password);
        return this;
    }

    public RequestSpecification build() {
        return builder.build();
    }

    public RequestSpecification build(String specName) {
        log.info("Сформирована спецификация: {}", specName);
        return builder.build();
    }
}
