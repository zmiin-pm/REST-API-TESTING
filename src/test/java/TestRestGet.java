import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class TestRestGet {
    private static final String BASE_URL = "http://petstore.swagger.io/v2";

    // Tests for GET request

    @Test
    public void sendGETRequestAndValidateResponse() {
        given()
                .queryParam("status", "available") // Parameter "status" = "available"
                .when()
                .get(BASE_URL + "/pet/findByStatus") // send GET request to find pets by status
                .then()
                .statusCode(200) // validate that request was received
                .body("[0].id", notNullValue()) // check that first object from array has not null id
                .body("[0].category.name", notNullValue())  // check that first object from array has not null category
                .body("[0].status", equalTo("available")); // check that first object from array has status  = available
    }

    @Test
    public void sendGETRequestByPassingQueryParameterInURL() {
        given()
                .when()
                .get(BASE_URL + "/pet/findByStatus?status=sold") // sending request with parameter in it
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue())
                .body("[0].category.name", notNullValue())
                .body("[0].status", equalTo("sold"));
    }

    @Test
    public void sendGETRequestAndRetrieveValueFromBody() {
        String status = given()
                .queryParam("status", "sold")
                .when()
                .get(BASE_URL + "/pet/findByStatus")
                .then()
                .extract()
                .path("[0].status"); // Extracting the value from JSON by path

        if (status == null)  // check that parameter not null
            throw new RuntimeException("Status is Empty!!!");
    }

    @Test
    public void sendAGETRequestAndStoreTheResponse() {
        Response response = given()
                .queryParam("status", "sold")
                .when()
                .get(BASE_URL + "/pet/findByStatus"); // GET response like response type var

        Assert.assertEquals(response.getStatusCode(), 200); // get StatusCode from response
    }
}
