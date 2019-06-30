package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class FindUsersTest extends FunctionalTests {

    private static final String FIND_USER_API = "/blog/user/find";

    @Test
    public void shouldNotContainUsersIfIsRemoved() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .param("searchString", "test@domain.com")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size", is(0))
                   .when()
                   .get(FIND_USER_API);
    }

    @Test
    public void shouldContainUsersWithStatusNewOrConfirmed() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .param("searchString", "john123@domain.com")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size", is(1))
                   .when()
                   .get(FIND_USER_API);
    }
}
