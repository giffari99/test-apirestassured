import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DetailTest {


    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQ5MWU0MzRmNTk2ZWUxOTQyMDE1YzYwNDdhYWQ3YSIsIm5iZiI6MTcyOTc3MzE2NS40Njk1NzksInN1YiI6IjY3MTkwODg2Yzc4MDJjYzUwMzU5YjIxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.X8JNmQf6vKWxTaMfuLBz0yVpFA2JTafRI8sVRErFw18";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testGetMovieDetails(){
        given()
                .header("authorization", myToken)
                .when()
                .get("/movie/1359227")
                .then()
                .statusCode(200)
                .body("id", equalTo(1359227))
                .body("release_date",equalTo("2024-10-17"))
                .body("title",equalTo("LEGO Marvel Avengers: Mission Demolition"));
    }
}