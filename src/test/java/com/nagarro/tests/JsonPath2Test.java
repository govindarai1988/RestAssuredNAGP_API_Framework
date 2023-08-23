package com.nagarro.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPath2Test {

    @Test(priority = 1, description = "Verifying get User API Test for complex JSON")
    public void getUsersAPITest() {

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

        JsonPath jsonPath = new JsonPath(response);

        System.out.println("Second Data Email: " + jsonPath.getString("data[1].email"));

        int numberOfRecords = jsonPath.getInt("data.size()");
        System.out.println("Number of records: " + numberOfRecords);

        for (int i = 0; i < numberOfRecords; i++) {
            if (jsonPath.getInt("data[" + i + "].id") == 8) {
                System.out.println(jsonPath.getString("data[" + i + "].email"));
                Assert.assertNull(jsonPath.getString("data[" + i + "].email"));
            }


        }

        List<String> emailAddress = jsonPath.getList("data.email");
        System.out.println("List of Email Address: " + emailAddress);


        Map<String, String> map = jsonPath.getMap("data[5]");
        System.out.println("Map: " + map);

    }

}
