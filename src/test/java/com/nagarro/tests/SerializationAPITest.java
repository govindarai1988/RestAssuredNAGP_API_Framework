package com.nagarro.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SerializationAPITest {
    @Test(priority = 1, description = "Verifying Create user API")
    public void serializeAPITest() {

        // Serialization -> in Rest Assured is a process of converting "Java Object into Request Body (Payload)" -> we use setters

        /* Advantages are:
        a. Easy to parse and extract (JSON/XML) values if they are wrapped in Java Objects
        b. User friendly methods can be created which make code more readable.
        c. Reusability of code
        */

        /* Libraries that are required:

        For Json, we require either of Jackson, Jackson2, Gson in the class path
        For xml, we require JAXB
         */


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
                        "\"email\":\"jasmeet.chadha@1236.com\", \n" +
                        "\"status\":\"active\"\n" +
                        "}")
                .when()
                .post("users")
                .then().log().all().assertThat().body("name", equalTo("Jasmeet Chadha")).extract().response().asString();


    }
}
