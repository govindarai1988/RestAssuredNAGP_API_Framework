package com.nagarro.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JsonPathTest {

    @Test(priority = 1, description = "Verifying Create user API have simple JSON")
    public void createUsersTest() {

        RestAssured.baseURI = "https://gorest.co.in/public/v2/";

        // given -> we usually input all the details like headers, parameters, request body
        // when -> used to submit the API (GET, POST, PUT or DELETE)
        // then -> is used to validate or assert response
        String userResponse = given()
                .header("Authorization", "Bearer 11a821b29b57a8ae65708b5fcbaa2d44dc69554b89360f0eeeaa096b748dac04")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .body("{\"name\":\"Jasmeet Chadha\", \n" +
                        "\"gender\":\"male\", \n" +
                        "\"email\":\"jasmeet.chadha@211.com\", \n" +
                        "\"status\":\"active\"\n" +
                        "}")
                .when()
                .post("users")
                .then().log().all().assertThat().body("name", equalTo("Jasmeet Chadha")).extract().response().asString();

        JsonPath jsonPath = new JsonPath(userResponse);
        System.out.println(jsonPath.getString("name"));
        System.out.println(jsonPath.getInt("id"));

        int userId = jsonPath.getInt("id");
        Assert.assertNotNull(userId);

        given()
                .header("Authorization", "Bearer 11a821b29b57a8ae65708b5fcbaa2d44dc69554b89360f0eeeaa096b748dac04")
                .pathParam("userId", userId)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .delete("users/{userId}")
                .then().assertThat().statusCode(204).extract().response().asString();


    }
}
