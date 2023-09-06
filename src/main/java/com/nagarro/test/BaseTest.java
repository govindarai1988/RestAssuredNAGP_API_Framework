package com.nagarro.test;
import com.nagarro.api.AuthApi;
import com.nagarro.api.PingApi;
import com.nagarro.payload.AuthRequestPayload;
import com.nagarro.payload.AuthResponsePayload;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import  org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaseTest {
    protected final Faker faker = new Faker();
    protected String token;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeAll
    void testHealthCheckReturns201() {
        Response response = PingApi.healthCheck();
        assertThat(response.statusCode(), equalTo(SC_CREATED));
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
    }

    @BeforeEach
    void testCreateTokenReturns200() {
        AuthRequestPayload authRequestPayload =
                AuthRequestPayload.builder().username("admin").password("password123").build();

        Response response = AuthApi.createToken(authRequestPayload);
        token = response.as(AuthResponsePayload.class).getToken();

        assertThat(response.statusCode(), equalTo(SC_OK));
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
    }
}
