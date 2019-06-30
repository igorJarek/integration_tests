package edu.iis.mto.blog.rest.test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class LikePostTest extends FunctionalTests {
    private static final String ADD_LIKE_TO_POST_API = "/blog/user/{userId}/like/{postId}";

    @Test
    public void apiShouldReturnStatusForbiddenIfUserIsRemoved() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 3, 1);
    }

    @Test
    public void apiShouldReturnStatusForbiddenIfUserIsNew() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 2, 3);
    }

    @Test
    public void apiShouldReturnStatusOkIfUserIsConfirmed() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 1, 1);
    }

    @Test
    public void userShouldNotNotLikeHisOwnPost() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_BAD_REQUEST)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 4, 2);
    }


    @Test
    public void apiShouldNotChangePostStatusWhenWeLikeItTwice() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 1, 1);

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(ADD_LIKE_TO_POST_API, 1, 1);
    }
}
