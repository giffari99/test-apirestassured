import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MoviePopularTest {

    private static final Logger log = LoggerFactory.getLogger(MoviePopularTest.class);
    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQ5MWU0MzRmNTk2ZWUxOTQyMDE1YzYwNDdhYWQ3YSIsIm5iZiI6MTcyOTc3MzE2NS40Njk1NzksInN1YiI6IjY3MTkwODg2Yzc4MDJjYzUwMzU5YjIxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.X8JNmQf6vKWxTaMfuLBz0yVpFA2JTafRI8sVRErFw18";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test(priority = 1)
    public void testMoviePopular(){

        RequestSpecification request = RestAssured.given();
        request.queryParam("language","en-us");
        request.queryParam("page","1");
        request.header("Authorization",myToken);
        Response response = request.get("/movie/popular");
        int statusCode = response.statusCode();
        int page = response.getBody().jsonPath().getInt("page");
        String title = response.getBody().jsonPath().getString("results.title[3]");
        System.out.println(statusCode);
        System.out.println(page);
        System.out.println(title);
        Assert.assertEquals(statusCode,200);
        Assert.assertEquals(page,1);

        //mengandung daftar film yang sedang diputar sekarang
        List<String> actual = response.getBody().jsonPath().getList("results.original_title");
        Assert.assertTrue(actual.contains("Alien: Romulus"));
        //Pastikan bahwa setiap film dalam daftar memiliki properti seperti "title movie".
        Assert.assertEquals(actual.size(), 20);
        Assert.assertEquals(response.getBody().jsonPath().getList("results.overview").size(), 20);

    }


    @Test(priority = 2)
    public void testInvalidToken(){
        String invalidToken = "Bearer invalid_token";

        given()
                .queryParam("language","en-US")
                .queryParam("page","1")
                .header("Authorization",invalidToken)
                .when()
                .get("/movie/popular")
                .then()
                .statusCode(401);
    }

    @Test(priority = 3)
    public void testInvalidUrl(){
        given()
                .queryParam("language","en-US")
                .queryParam("page","1")
                .header("Authorization",myToken)
                .when()
                .get("/movie/Invalid")
                .then()
                .statusCode(404);
    }





}
