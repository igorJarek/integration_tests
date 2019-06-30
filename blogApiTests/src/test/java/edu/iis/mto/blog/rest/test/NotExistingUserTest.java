package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class NotExistingUserTest {

    private static final String USER_API = "/blog/user/";

    @Test
    public void tryingToFindNotExistingUserShouldGenerateHTTPStatus404(){

        String id = "666";

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_NOT_FOUND)
                   .when()
                   .get(USER_API + id);
    }
}
