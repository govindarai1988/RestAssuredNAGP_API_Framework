package APITests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import app.hooks.BookingApi;
import app.configs.ExtentManager;
import app.payloads.BookingDates;
import app.payloads.BookingRequestPayload;
import app.payloads.BookingResponsePayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


class GetBookingTests extends BaseTest  {

    BookingRequestPayload createBookingRequestPayload() {

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        return BookingRequestPayload.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .totalPrice(faker.number().numberBetween(100, 500))
                .depositPaid(true)
                .bookingDates(
                        BookingDates.builder().checkin(currentDate).checkout(currentDate).build())
                .additionalNeeds("None")
                .build();

    }

    @Test
    void testGetAllBookingIdsReturns200() {

        ExtentTest test = ExtentManager.createTest("testGetAllBookingIdsReturns200");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        test.log(Status.INFO, "Executing method: " + methodName);
        try {
            Response response = BookingApi.getAllBookingIds();
            assertThat(response.statusCode(), equalTo(SC_OK));
            test.log(Status.PASS,"Test Step Passed");
        }
        catch (AssertionError e) {
            // Assertion failed, mark the test as failed
            test.log(Status.FAIL, "Test step failed: " + e.getMessage());
            throw e;
        } // Re-throw the assertion error to mark the overall test as failed
        logger.info(methodName+" has been executed");

    }

    @Test
    void testGetAllBookingIdsReturnsNonEmptyArray() {
        ExtentTest test = ExtentManager.createTest("testGetAllBookingIdsReturnsNonEmptyArray");
        BookingResponsePayload[] bookingResponsePayload = BookingApi.getAllBookingIds().as(BookingResponsePayload[].class);
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        test.log(Status.INFO, "Executing method: " + methodName);

        try{
            assertThat(bookingResponsePayload.length,greaterThan(0));
            test.log(Status.PASS,"Test Step Passed");

        }
        catch (AssertionError e) {
            // Assertion failed, mark the test as failed
            test.log(Status.FAIL, "Test step failed: " + e.getMessage());
            throw e;
        } // Re-throw the assertion error to mark the overall test as failed
        logger.info(methodName+" has been executed");
    }

    @Test
    void testGetBookingIdsByNameReturns200() {
        ExtentTest test = ExtentManager.createTest("testGetBookingIdsByNameReturns200");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        test.log(Status.INFO, "Executing method: " + methodName);
        try {
            Response response = BookingApi.getBookingIdsByName("sally", "brown");
            assertThat(response.statusCode(), equalTo(SC_OK));
            test.log(Status.PASS, "Test Step Passed");
        }
        catch (AssertionError e) {
        // Assertion failed, mark the test as failed
        test.log(Status.FAIL, "Test step failed: " + e.getMessage());
        throw e;
    } // Re-throw the assertion error to mark the overall test as failed
        logger.info(methodName+" has been executed");

    }

    @Test
    void testGetBookingIdsByDateReturns200() {
        ExtentTest test = ExtentManager.createTest("testGetBookingIdsByDateReturns200");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        test.log(Status.INFO, "Executing method: " + methodName);
        try{
        Response response = BookingApi.getBookingIdsByDate("2014-03-13", "2014-05-21");
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.log(Status.PASS,"Test Step Passed");
        }
        catch (AssertionError e) {
            // Assertion failed, mark the test as failed
            test.log(Status.FAIL, "Test step failed: " + e.getMessage());
            throw e;
        } // Re-throw the assertion error to mark the overall test as failed

        logger.info(methodName+" has been executed");

    }

    @Test
    void testGetBookingByIdReturns200() {
        ExtentTest test = ExtentManager.createTest("testGetBookingByIdReturns200");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        test.log(Status.INFO, "Executing method: " + methodName);
        try{
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        Response response = BookingApi.getBookingById(id);
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.log(Status.PASS,"Test Step Passed");
        }
        catch (AssertionError e) {
            // Assertion failed, mark the test as failed
            test.log(Status.FAIL, "Test step failed: " + e.getMessage());
            throw e;
        } // Re-throw the assertion error to mark the overall test as failed
        logger.info(methodName+" has been executed");

    }

}
