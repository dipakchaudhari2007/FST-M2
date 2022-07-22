package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubTest {
    RequestSpecification requestspec;
    String SSHkey ="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQChOUv7Jkb+PXUYXVV0ljIC7G71cJXcXkf+8nWFVgeg4XT4wZEKxG9hFLVen3d8qHaO7jZ6H3sZF/TkRtHYDhh+ofssMjeUgMZESk1wTWmdSjquCqsTBS8XAFFsrXo6O0ovQXqnqYwKWZC7oDDgoJFDWaqzs+ixMtosDH3FB4Y96nJlSyqj5qRNHENLOoNBdgrFV+Z+fJ0nlC3ote4HKE4qwInXQg9+P3ftvzLHgxmivsyo1SXxSwFe+vwD0TdYhsuU1iLeckr5j1B3FJMH78QOpjeomTVfc8AMqEjTVOO9rAeU9KGpX+1XQsEHAKHRrrNfFltjieBdtBQqUnoO2xtT";
    int id=0;
    @BeforeClass
    public void setUp(){
        requestspec= new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                addHeader("Authorization","token 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").
                setBaseUri("https://api.github.com").build();
    }
    @Test(priority = 1)
    public void postrequest(){
        String reqbody ="{\"title\": \"TestAPIKey\",\"key\":\""+ SSHkey +"\"}";
        Response response = given().spec(requestspec).body(reqbody).
                when().post("/user/keys");
        System.out.println(response.getBody().asPrettyString());
        id=response.then().extract().body().path("id");
        response.then().statusCode(201);
    }
    @Test(priority = 2)
    public void getrequest(){
        Response response = given().spec(requestspec).pathParam("keyId",id).when().get("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200);
    }
    @Test(priority = 3)
    public void deleterequest(){
        Response response = given().spec(requestspec).pathParam("keyId",id).when().delete("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(204);
    }
}