package APITests;

import com.aventstack.extentreports.ExtentTest;
import com.nagarro.api.BookingApi;
import com.nagarro.config.ExtentManager;
import com.nagarro.payload.BookingDates;
import com.nagarro.payload.BookingRequestPayload;
import com.nagarro.payload.BookingResponsePayload;


import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class ApiTest extends BaseTest {

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
    void testGetAllBookingIdsReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testGetAllBookingIdsReturns200");
        Response response = BookingApi.getAllBookingIds();
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("passed");
        logger.info(testInfo.getDisplayName()+" has been executed");

    }

    @Test
    void testGetAllBookingIdsReturnsNonEmptyArray(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testGetAllBookingIdsReturnsNonEmptyArray");
        BookingResponsePayload[] bookingResponsePayload = BookingApi.getAllBookingIds().as(BookingResponsePayload[].class);
        if(bookingResponsePayload.length>0){
            test.pass("Pass");
        }
        else{
            test.fail("Fail");
        }
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testGetBookingIdsByNameReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testGetBookingIdsByNameReturns200");
        Response response = BookingApi.getBookingIdsByName("sally", "brown");
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testGetBookingIdsByDateReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testGetBookingIdsByDateReturns200");
        Response response = BookingApi.getBookingIdsByDate("2014-03-13", "2014-05-21");
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testGetBookingByIdReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testGetBookingByIdReturns200");
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        Response response = BookingApi.getBookingById(id);
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testCreateBookingReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testCreateBookingReturns200");
        Response response = BookingApi.createBooking(createBookingRequestPayload());
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testCreateBookingReturnsCorrectDetails(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testCreateBookingReturnsCorrectDetails");
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        BookingResponsePayload bookingResponsePayload =
                BookingApi.createBooking(bookingRequestPayload).as(BookingResponsePayload.class);
        assertThat(
                bookingRequestPayload.equals(bookingResponsePayload.getBookingRequestPayload()),
                is(true));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testUpdateBookingReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testUpdateBookingReturns200");
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));
        Response response = BookingApi.updateBooking(bookingRequestPayload, id, token);
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testUpdateBookingReturnsCorrectDetails(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testUpdateBookingReturnsCorrectDetails");
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        bookingRequestPayload.setFirstName(faker.name().firstName());
        bookingRequestPayload.setLastName(faker.name().lastName());
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));
        BookingRequestPayload bookingResponsePayload =
                BookingApi.updateBooking(bookingRequestPayload, id, token)
                        .as(BookingRequestPayload.class);
        assertThat(bookingRequestPayload.equals(bookingResponsePayload), is(true));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testPartialUpdateBookingReturns200(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testPartialUpdateBookingReturns200");
        BookingRequestPayload bookingRequestPayload = createBookingRequestPayload();
        int id =
                BookingApi.createBooking(bookingRequestPayload)
                        .as(BookingResponsePayload.class)
                        .getBookingId();
        bookingRequestPayload.setTotalPrice(faker.number().numberBetween(100, 500));

        Response response = BookingApi.partialUpdateBooking(bookingRequestPayload, id, token);
        assertThat(response.statusCode(), equalTo(SC_OK));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }

    @Test
    void testDeleteBookingReturns201(TestInfo testInfo) {
        ExtentTest test = ExtentManager.createTest("testDeleteBookingReturns201");
        int id =
                BookingApi.createBooking(createBookingRequestPayload())
                        .as(BookingResponsePayload.class)
                        .getBookingId();

        Response response = BookingApi.deleteBooking(id, token);
        assertThat(response.statusCode(), equalTo(SC_CREATED));
        test.pass("Pass");
        logger.info(testInfo.getDisplayName()+" has been executed");
    }
}
