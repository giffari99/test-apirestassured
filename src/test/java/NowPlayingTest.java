import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NowPlayingTest {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQ5MWU0MzRmNTk2ZWUxOTQyMDE1YzYwNDdhYWQ3YSIsIm5iZiI6MTcyOTc3MzE2NS40Njk1NzksInN1YiI6IjY3MTkwODg2Yzc4MDJjYzUwMzU5YjIxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.X8JNmQf6vKWxTaMfuLBz0yVpFA2JTafRI8sVRErFw18";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }


    // -------------------- Static given()
    @Test
    public void testNowPlaying(){
        RequestSpecification request = RestAssured.given();
        request.queryParam("language","en-us");
        request.queryParam("page","1");
        request.header("Authorization",myToken);
        Response response = request.get("/movie/now_playing");
        int statusCode = response.statusCode();
        int page = response.getBody().jsonPath().getInt("page");
        String title = response.getBody().jsonPath().getString("results.title[1]");
        System.out.println(statusCode);
        System.out.println(page);
        System.out.println(title);
        Assert.assertEquals(statusCode,200);
        Assert.assertEquals(page,1);
        Assert.assertEquals(title,"Venom: The Last Dance");



    }


    @Test
    public void testInvalidUrl(){
        given()
                .queryParam("language","en-US")
                .queryParam("page", "1")
                .header("Authorization",myToken)
                .when()
                .get("movie/Invalid")   // invalidEndPoint
                .then()
                .statusCode(404);



    }
}

