package APITests;

import app.hooks.AuthApi;
import app.hooks.PingApi;
import app.configs.ExtentManager;
import app.payloads.AuthRequestPayload;
import app.payloads.AuthResponsePayload;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
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


    public static String readProperty(String key) {

        Properties properties = new Properties();

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {

            properties.load(fis);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return properties.getProperty(key);

    }

    @BeforeEach
    void testCreateTokenReturns200() {
        //ExtentTest test = ExtentManager.createTest(this.getClass().getSimpleName());

        AuthRequestPayload authRequestPayload =
                AuthRequestPayload.builder().username(readProperty("username")).password(readProperty("password")).build();

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
