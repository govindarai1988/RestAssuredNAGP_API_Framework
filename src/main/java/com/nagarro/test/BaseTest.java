package com.nagarro.test;

import com.nagarro.api.AuthApi;
import com.nagarro.api.PingApi;
import com.nagarro.config.ExtentManager;
import com.nagarro.payload.AuthRequestPayload;
import com.nagarro.payload.AuthResponsePayload;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaseTest {
    protected final Faker faker = new Faker();
    protected String token;
    public static final Logger logger = LogManager.getLogger(BaseTest.class);





    @BeforeAll
    void testHealthCheckReturns201() {

        ExtentManager.getInstance();
        Response response = PingApi.healthCheck();
        assertThat(response.statusCode(), equalTo(SC_CREATED));
        logger.info("Test case execution has started");


    }

    @BeforeEach
    void testCreateTokenReturns200() {
        //ExtentTest test = ExtentManager.createTest(this.getClass().getSimpleName());

        AuthRequestPayload authRequestPayload =
                AuthRequestPayload.builder().username("admin").password("password123").build();

        Response response = AuthApi.createToken(authRequestPayload);
        token = response.as(AuthResponsePayload.class).getToken();
        assertThat(response.statusCode(), equalTo(SC_OK));

    }

    @AfterAll
    void afterall(){
        ExtentManager.flushReport();
        logger.info("all test executed");
    }
}
