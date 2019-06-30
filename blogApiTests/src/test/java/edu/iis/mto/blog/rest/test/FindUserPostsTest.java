package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class FindUserPostsTest extends FunctionalTests {

    private static final String FIND_USER_POSTS_API = "/blog/user/{id}/post";

    @Test
    public void findingPostsForRemovedUserReturnBadRequest() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_BAD_REQUEST)
                   .when()
                   .get(FIND_USER_POSTS_API, 3);
    }

    @Test
    public void findingPostsForNewUserReturnOk() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .get(FIND_USER_POSTS_API, 2);
    }

    @Test
    public void shouldProperCountPost() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .body("likesCount", hasItem(1))
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .get(FIND_USER_POSTS_API, 2);
    }
}
