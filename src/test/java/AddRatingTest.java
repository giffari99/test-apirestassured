import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddRatingTest {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQ5MWU0MzRmNTk2ZWUxOTQyMDE1YzYwNDdhYWQ3YSIsIm5iZiI6MTcyOTc3MzE2NS40Njk1NzksInN1YiI6IjY3MTkwODg2Yzc4MDJjYzUwMzU5YjIxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.X8JNmQf6vKWxTaMfuLBz0yVpFA2JTafRI8sVRErFw18";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testPostMovieRating(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("value", 8.0);

        RequestSpecification request = RestAssured.given();
        request.header("authorization", myToken);
        request.header("content-type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post("/movie/1359227/rating");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getBody().jsonPath().getBoolean("success"));
        Assert.assertTrue(response.getBody().jsonPath().getString("status_message").toLowerCase().contains("success"));
        System.out.println(response.getBody().jsonPath().getString("status_message"));
    }


    @Test
    public void testNoToken(){
        given()
                .get("/movie/1359227/rating")
                .then()
                .statusCode(401);
    }

}
