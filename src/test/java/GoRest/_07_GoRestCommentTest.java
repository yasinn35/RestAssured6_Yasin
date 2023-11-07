package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _07_GoRestCommentTest {

 // GoRest Comment ı API testini yapınız

    Faker randomUretici=new Faker();
    int commentId=0;
    RequestSpecification reqSpec;
    @BeforeClass
    public void setup(){

        baseURI = "https://gorest.co.in/public/v2/comments";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 78eb13382e629312ad3041e0f3f07f788719d1f47c741f2776daeb36d8dc7057")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void createCommentMap(){

        String rndFullName=randomUretici.name().fullName();
        String rndemail=randomUretici.internet().emailAddress();

        Map<String,String> newComment=new HashMap<>();
        newComment.put("name",rndFullName);
        newComment.put("post_id","82477");
        newComment.put("email",rndemail);
        newComment.put("body","en büyük beşiktaş");

        commentId=
                given()// giden body, token, contentType
                        .spec(reqSpec)
                        .body(newComment) // giden body


                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

        ;
        System.out.println("userID = " + commentId);


    }

    @Test(dependsOnMethods = "createCommentMap")
    public void getCommentById(){

        given()

                .spec(reqSpec)

                .when()
                .get(""+commentId)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(commentId))

        ;
    }

    @Test(dependsOnMethods = "getCommentById")
    public void updatecomment(){
        Map<String,String> updateComment=new HashMap<>();
        updateComment.put("body","En büyük ksk");
        given()
                .spec(reqSpec)
                .body(updateComment)


                .when()
                .put(""+commentId)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(commentId))
                .body("body",equalTo("En büyük ksk"))



        ;


    }


    @Test(dependsOnMethods = "updatecomment")
    public void deleteComment(){


        given()
                .spec(reqSpec)



                .when()
                .delete(""+commentId)

                .then()
                //.log().all()
                .statusCode(204)

        ;

    }

    @Test(dependsOnMethods = "deleteComment")
    public void deleteCommentNegative(){


        given()
                .spec(reqSpec)



                .when()
                .delete(""+commentId)

                .then()
                .statusCode(404)

        ;

    }


}
