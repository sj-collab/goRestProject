package com.qa.rest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.rest.utils.BaseClass;
import com.qa.rest.utils.PropertyManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

public class CreateNewUserTest extends BaseClass{

	
	private String accessToken = PropertyManager.getInstance().getAccessToken();
	private String userName = PropertyManager.getInstance().getUserName();
	
	@Test(priority=1)
	public void testCreateNewUser() {
		RequestSpecification request = setBaseURL();
		String bodyString = getJsonObjForUser().toJSONString();

		given(request)
		.header(
				"Authorization",
	              "Bearer " + accessToken
				)
			.contentType("application/json")
			.body(bodyString)
		.expect()
        	.statusCode(200)
		.when()
        .post("/public-api/users")
        .then()
        .contentType(ContentType.JSON)
        .extract()
        .response();
		
		Response response = given(request)
		.queryParam("name", userName)
		.get("/public-api/users");

		String jsonString = response.asString();
		System.out.println(response.getStatusCode());
		Assert.assertEquals(jsonString.contains(userName), true);
	}

}
