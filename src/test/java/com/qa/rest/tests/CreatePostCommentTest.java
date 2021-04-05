package com.qa.rest.tests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.rest.utils.BaseClass;
import com.qa.rest.utils.PropertyManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreatePostCommentTest extends BaseClass{
	private String accessToken = PropertyManager.getInstance().getAccessToken();
	private String updated_name = PropertyManager.getInstance().getUpdatedUserName();
	private String userId= getIdForUser(updated_name);
	
	@Test(priority=3)
	public void testCreatePostCommentTest() {
		RequestSpecification request = setBaseURL();
		String bodyString = getJsonObjForPost().toJSONString();
		String bodyStringComments = getJsonObjForComment(updated_name).toJSONString();
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
		.post("/public-api/users/"+userId+"/posts")
        .then()
        .extract()
        .response();


		String postIdString = getIdForPost(updated_name);

		given(request)
		.header(
			"Authorization",
              "Bearer " + accessToken
			)
	.and()
		.contentType("application/json")
	.and()
		.body(bodyStringComments)
	.expect()
    	.statusCode(200)
	.when()
	.post("/public-api/posts/"+postIdString+"/comments")
    .then()
    .extract()
    .response();
		
		Response response = given(request)
		.get("/public-api/posts/"+postIdString+"/comments");

		String jsonString = response.asString();
		System.out.println(response.getStatusCode());
		Assert.assertEquals(jsonString.contains(updated_name), true);
	}
}
