package GoRest;

import Model.User;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _06_GoRestUsersTest {

//    https://gorest.co.in/public/v2/users
//
//
//
   // {"name":"yasin kaya", "gender":"male", "email":"kayay@gmail.com", "status":"active"}
//
//
//   14ce664f262cd40e5588cda7efda2a3dbefb2112a13eb029064e122248228da0

    Faker randomUretici=new Faker();
    int userID=0;
    RequestSpecification reqSpec;
    @BeforeClass
    public void setup(){

        baseURI = "https://gorest.co.in/public/v2/users";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 78eb13382e629312ad3041e0f3f07f788719d1f47c741f2776daeb36d8dc7057")
                .setContentType(ContentType.JSON)
                .build();

    }

//    @Test
//    public void createUserJSon(){
//
//        String rndFullName=randomUretici.name().fullName();
//        String rndemail=randomUretici.internet().emailAddress();
//
//         userID=
//        given()// giden body, token, contentType
//                .header("Authorization","Bearer 78eb13382e629312ad3041e0f3f07f788719d1f47c741f2776daeb36d8dc7057")
//                .body("{\"name\":\""+rndFullName+"\", \"gender\":\"male\", \"email\":\""+rndemail+"\", \"status\":\"active\"}") // giden body
//                .contentType(ContentType.JSON)
//
//                .when()
//                .post("https://gorest.co.in/public/v2/users")
//
//                .then()
//                .log().body()
//                .statusCode(201)
//                .extract().path("id")
//
//        ;
//        System.out.println("userID = " + userID);
//
//
//
//    }




//    @Test
//    public void createUserClass(){
//
//        String rndFullName= randomUretici.name().fullName();
//        String rndEmail= randomUretici.internet().emailAddress();
//
//        User newUser=new User();
//        newUser.name=rndFullName;
//        newUser.email=rndEmail;
//        newUser.gender="male";
//        newUser.status="active";
//
//        userID=
//                given() // giden body, token, contentType
//                        .header("Authorization","Bearer 787c83039875452ce6a32a7b73e7a10c1d4443273522652da0f13d5e76a27713")
//                        .body(newUser) // giden body
//                        .contentType(ContentType.JSON)
//
//                        .when()
//                        .post("https://gorest.co.in/public/v2/users")
//
//                        .then()
//                        .log().body()
//                        .statusCode(201)
//                        .extract().path("id");
//        ;
//        System.out.println("userID = " + userID);
//    }



    @Test
    public void createUserMap(){

        String rndFullName=randomUretici.name().fullName();
        String rndemail=randomUretici.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("gender","male");
        newUser.put("email",rndemail);
        newUser.put("status","active");

        userID=
                given()// giden body, token, contentType
                        .spec(reqSpec)
                        .body(newUser) // giden body


                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

        ;
        System.out.println("userID = " + userID);



    }





    @Test(dependsOnMethods = "createUserMap")
    public void getUserById(){

        given()

                .spec(reqSpec)

                .when()
                .get(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))

                ;
    }

    @Test(dependsOnMethods = "getUserById")
    public void updateUser(){
        Map<String,String> updateUser=new HashMap<>();
        updateUser.put("name","yasin kaya");
        given()
                .spec(reqSpec)
                .body(updateUser)


                .when()
                .put(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo("yasin kaya"))



        ;


    }


    @Test(dependsOnMethods = "updateUser")
    public void deleteUser(){


        given()
                .spec(reqSpec)



                .when()
                .delete(""+userID)

                .then()
                //.log().all()
                .statusCode(204)

        ;

    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){


        given()
                .spec(reqSpec)



                .when()
                .delete(""+userID)

                .then()
                .statusCode(404)

        ;

    }


}
