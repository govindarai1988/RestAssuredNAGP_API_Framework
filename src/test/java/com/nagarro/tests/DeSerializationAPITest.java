package com.nagarro.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeSerializationAPITest {

    @Test(priority = 1, description = "Verifying get User API Test")
    public void deserializeAPITest() throws JsonProcessingException {

        // Deserialization -> is process to convert "Response Body to Java Object"   -> we use getters because we are extracting the values from response

        /* Advantages are:
        a. Easy to parse and extract (JSON/XML) values if they are wrapped in Java Objects
        b. User friendly methods can be created which make code more readable.
        c. Reusability of code
        */

        /* Libraries that are required:

        For Json, we require either of Jackson, Jackson2, Gson in the class path
        For xml, we require JAXB
         */

        RestAssured.baseURI = "https://reqres.in/";

        // given -> we usually input all the details like headers, parameters, request body
        // when -> used to submit the API (GET, POST, PUT or DELETE)
        // then -> is used to validate or assert response

        String response = given()
                .queryParam("page", "2")
                .header("Accept", "*/*")
                .log().all()
                .when()
                .get("api/users")
                .then().log().all().extract().response().asString();

        System.out.println(response);

    }
}
