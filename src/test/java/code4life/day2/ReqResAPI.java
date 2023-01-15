package code4life.day2;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import  static    io.restassured.RestAssured.*;
import  static    io.restassured.matcher.RestAssuredMatchers.*;
import static      org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ReqResAPI {

    String baseUrl = "https://reqres.in/";


    @Test
    public void getAllUsers(){
        given()
                .baseUri(baseUrl)
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data[3].first_name", equalTo("Byron"))
                .body("data[3].avatar", equalTo("https://reqres.in/img/faces/10-image.jpg"))
                .body("data[3].last_name", equalTo("Fields"))
                .body("data.first_name", hasItems("George", "Rachel")).log().all();
    }

    @Test
    public void getSingleUser(){
        given()

                .baseUri(baseUrl)
                .when()
                .get("/api/users/23").then().assertThat().statusCode(404).log().all();
    }

    @Test
    public void getResource(){
        given()
                .baseUri(baseUrl)
                .when()
                .get("/api/unknown").then().assertThat().statusCode(200).log().all();
    }

    @Test
    public void createNewUserWithJSonConverter(){
//        Map<String, Object> user = new HashMap<>();
//        user.put("name", "Biden");
//        user.put("job", "president");

        JSONObject object = new JSONObject();
        object.put("name", "Biden");
        object.put("job", "president");

        given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(object.toJSONString())
                .when()
                .post("/api/users").then().assertThat().statusCode(201).log().all();

    }

    @Test
    public void UpdateNewUserWithJSonConverter(){
//        Map<String, Object> user = new HashMap<>();
//        user.put("name", "Biden");
//        user.put("job", "president");

        JSONObject object = new JSONObject();
        object.put("name", "Biden");
        object.put("job", "president");

        given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(object.toJSONString())
                .when()
                .put("/api/users/2").then().assertThat().statusCode(200).log().all();

    }








    @Test
    public void getSingleResource(){
        given()
                .baseUri(baseUrl)
                .when()
                .get("/api/unknown/23").then().assertThat().statusCode(404)
                .log().all();
    }

    @Test
    public void deleteUser(){
        given()
                .baseUri(baseUrl)
                .when()
                .delete("/api/users/2").then().assertThat().statusCode(204).log().all();
    }




}
