package app.hooks;
import app.payloads.AuthRequestPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static app.urls.ApiEndpoints.AUTH_ENDPOINT;
import static app.urls.ApiEndpoints.BASE_URL;
import static io.restassured.RestAssured.given;

public final class AuthApi {
    private AuthApi() {}

    public static Response createToken(AuthRequestPayload authRequestPayload) {
        return given().contentType(ContentType.JSON)
                .body(authRequestPayload)
                .when()
                .post(BASE_URL + AUTH_ENDPOINT);
    }
}
