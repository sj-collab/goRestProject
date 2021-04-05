package com.qa.rest.tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.rest.utils.BaseClass;
import com.qa.rest.utils.PropertyManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RenameUserTest  extends BaseClass{

	private String accessToken = PropertyManager.getInstance().getAccessToken();
	private String userName = PropertyManager.getInstance().getUserName();
	private String updated_name = PropertyManager.getInstance().getUpdatedUserName();
	private String userId= getIdForUser(userName);
	
	@Test(priority=2)
	public void testRenameUserTest() {
		RequestSpecification request = setBaseURL();
		String bodyString = getJsonObjForRenameUser().toJSONString();
		
		given(request)
			.header(
				"Authorization",
	              "Bearer " + accessToken
				)
		.and()
			.contentType("application/json")
		.and()
			.body(bodyString)
		.when()
        .patch("/public-api/users/"+userId)
        .then()
        .extract()
        .response();
		
		Response response = given(request)
				.queryParam("name", updated_name)
				.get("/public-api/users");

				String jsonString = response.asString();
				System.out.println(response.getStatusCode());
				Assert.assertEquals(jsonString.contains(updated_name), true);
	}

}
