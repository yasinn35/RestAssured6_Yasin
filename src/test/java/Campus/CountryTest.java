package Campus;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CountryTest {

    String countrId = "";

    RequestSpecification reqSpec;
    Faker faker = new Faker();

    String rndName = faker.address().country() + faker.address().countryCode();
    String rndCode = faker.address().countryCode();

    @BeforeClass
    public void Setup() {
        baseURI = "https://test.mersys.io/";

        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .body(userCredential)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/auth/login")

                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();

        reqSpec = new RequestSpecBuilder()
                .addCookies(cookies)
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void createCountry() {
        Map<String, String> creatCountry = new HashMap<>();
        creatCountry.put("name", rndName);
        creatCountry.put("code", rndCode);
        //creatCountry.put("tranlateName","[]");

        countrId =
                given()
                        .spec(reqSpec)
                        .body(creatCountry)
                        //.log().all()

                        .when()
                        .post("school-service/api/countries/")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

        ;
    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {

        // Aynı countryName ve code gönderildiğinde kayıt yapılmadığını yani
        // negative test
        Map<String, String> creatCountry = new HashMap<>();
        creatCountry.put("name", rndName);
        creatCountry.put("code", rndCode);

        given()
                .spec(reqSpec)
                .body(creatCountry)
                .log().all()

                .when()
                .post("school-service/api/countries/")


                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"))

        ;
    }

    @Test(dependsOnMethods = "createCountryNegative")
    public void updateCountry() {

        String newCountryName = faker.number().digits(5);
        Map<String, String> creatCountry = new HashMap<>();
        creatCountry.put("id", countrId);
        creatCountry.put("name", newCountryName);
        creatCountry.put("code", "44256");
        //creatCountry.put("tranlateName","[]");

        given()
                .spec(reqSpec)
                .body(creatCountry)
                .log().all()

                .when()
                .put("school-service/api/countries/")


                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(newCountryName))

        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void DeleteCountry() {

        given()
                .spec(reqSpec)

                .when()
                .delete("school-service/api/countries/" + countrId)


                .then()
                .log().body()
                .statusCode(200)
        ;
    }


    @Test(dependsOnMethods = "updateCountry")
    public void DeleteCountryNegative() {

        given()
                .spec(reqSpec)

                .when()
                .delete("school-service/api/countries/" + countrId)


                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Country not found"))
        ;
    }



}
