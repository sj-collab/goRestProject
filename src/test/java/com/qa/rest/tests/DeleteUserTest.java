package com.qa.rest.tests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.rest.utils.BaseClass;
import com.qa.rest.utils.PropertyManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteUserTest extends BaseClass{
	private String accessToken = PropertyManager.getInstance().getAccessToken();
	private String updated_name = PropertyManager.getInstance().getUpdatedUserName();
	private String userId= getIdForUser(updated_name);
	
	@Test(priority=4)
	public void testDeleteUserTest() {
		RequestSpecification request = setBaseURL();
		given(request)
		.header(
			"Authorization",
              "Bearer " + accessToken
			)
	.and()
		.contentType("application/json")
	.expect()
    	.statusCode(200)
	.when()
    .delete("/public-api/users/"+userId)
    .then()
    .extract()
    .response();
		
		Response response = given(request)
				.get("/public-api/users/"+userId);

				String jsonString = response.asString();
				System.out.println(response.getStatusCode());
				Assert.assertEquals(jsonString.contains("Resource not found"), true);
	
		response = given(request)
						.get("/public-api/users/");

		jsonString = response.asString();
						System.out.println(response.getStatusCode());
						Assert.assertEquals(jsonString.contains(updated_name), false);
	}
}
