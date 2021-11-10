package com.kuzmin.resource;

import com.kuzmin.utils.TestContainerResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;

import javax.inject.Inject;
import java.sql.SQLException;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.delete;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class CartResourceTest {
    @Test
    void testFindAll() {
        get("/carts")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0));
    }

    @Test
    void testFindAllActiveCarts() {
        get("/carts/active").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testGetActiveCartForCustomer() {
        get("/carts/customer/3").then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .body(containsString("Peter"));
    }

    @Test
    void testFindById() {
        get("/carts/3").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("status"))
                .body(containsString("NEW"));
        get("/carts/100").then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testDelete() {
        get("/carts/active").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Jason"))
                .body(containsString("NEW"));
        delete("/carts/1").then()
                .statusCode(NO_CONTENT.getStatusCode());
        get("/carts/1").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Jason"))
                .body(containsString("CANCELED"));
    }
}
