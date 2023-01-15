package code4life.day1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class SampleBookApi {

    String baseUrl = "https://simple-books-api.glitch.me";

    String API_KEY ="aeb027dcae84dbfed30670a50cfe9b8768ab18acaa74d2fabef5fb0ca64065f0";


    @Test
    public void getStatusBookWithResponseObj(){
// we can store api the whole response inside Response API as an object to extract specific parts of api
        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get("/status");

        System.out.println("Status code for this test = " + response.statusCode());

        int stCode = response.getStatusCode();

        Assertions.assertEquals(200, stCode);



    }


    @Test
    public void getStatusWithoutResponseObj(){
        given()
                .baseUri(baseUrl)
                .when()
                .get("/status").then().statusCode(200);



    }

    @Test
    public void getListOfBooks(){

        given()
                .baseUri(baseUrl)
                .when()
                .get("/books").prettyPeek().then().assertThat().statusCode(200);
    }



    @Test
    public void getSingleBook(){
        given()
                .baseUri(baseUrl)
                .when()
                .get("/books/1").prettyPeek().then().assertThat().statusCode(200);
    }

    @Test
    public void submitOrder(){

        File submitFile = new File(System.getProperty("user.dir")+"/submit.json");


    given()
            .header("Authorization", "Bearer "+ API_KEY)
            .contentType(ContentType.JSON)
            .baseUri(baseUrl)
            .body(submitFile)
            .when()
            .post("/orders").prettyPeek().then().assertThat().statusCode(201);

    }

    @Test
    public void getAnOrder(){

        given()
                .header("Authorization", "Bearer "+ API_KEY)
                .baseUri(baseUrl)
                .when()
                .get("/orders/LTJnJlV-QmQqz30WP54y6").prettyPeek()
                .then().assertThat().statusCode(200);

    }


    @Test
    public void updateAnOrder(){

        File updateFile = new File(System.getProperty("user.dir")+"/update.json");

        given()
                .header("Authorization", "Bearer "+ API_KEY)
                .contentType(ContentType.JSON)
                .body(updateFile)
                .baseUri(baseUrl)
                .when()
                .patch("/orders/WFVq_v0IMs89PPSqpV-Ig").prettyPeek()
                .then().assertThat().statusCode(204);

    }

    @Test
    public void deleteAnOrder(){
        given()
                .header("Authorization", "Bearer "+ API_KEY)
                .baseUri(baseUrl)
                .when()
                .delete("/orders/LTJnJlV-QmQqz30WP54y6").prettyPeek().then()
                .assertThat().statusCode(204);

    }
}
