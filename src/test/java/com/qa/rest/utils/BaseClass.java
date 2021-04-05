package com.qa.rest.utils;

import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass extends PropertyManager{
	private static String urlParam = PropertyManager.getInstance().getURL();
	private static String user_name = PropertyManager.getInstance().getUserName();
	private static String updated_name = PropertyManager.getInstance().getUpdatedUserName();

	protected static RequestSpecification setBaseURL() {
		RestAssured.baseURI = urlParam;
		RequestSpecification request = RestAssured.given();
		return request;
	}
	
	@SuppressWarnings("unchecked")
	protected static JSONObject getJsonObjForUser() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", user_name);
		jsonObj.put("gender", "Male");
		jsonObj.put("email", user_name+"@test.com");
		jsonObj.put("status", "Active");
		return jsonObj;
	}
	
	@SuppressWarnings("unchecked")
	protected static JSONObject getJsonObjForRenameUser() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", updated_name);;
		return jsonObj;
	}
	
	@SuppressWarnings("unchecked")
	protected static JSONObject getJsonObjForPost() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title", "Testing Post");
		jsonObj.put("body", "Test Post Body");
		return jsonObj;
	}
	

	@SuppressWarnings("unchecked")
	protected static JSONObject getJsonObjForComment(String userName) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", userName);
		jsonObj.put("email", userName+"@test.com");
		jsonObj.put("body", "Test Comment Body");
		return jsonObj;
	}
	
	protected String getIdForUser(String userName) {
		Response res = given(setBaseURL())
		.queryParam("name", userName)
		.get("/public-api/users")
		.then()
		.statusCode(200)
		.extract()
		.response();
		JsonPath jsonPathEvaluator = res.jsonPath();
		String id = jsonPathEvaluator.get("data.id").toString();
		id = id.replaceAll("\\[", "").replaceAll("\\]","");
		return id;
	}
	protected String getIdForPost(String userName) {
		String userIdString= getIdForUser(userName);
		Response res = given(setBaseURL())
		.get("/public-api/users/"+userIdString+"/posts")
		.then()
		.statusCode(200)
		.extract()
		.response();
		JsonPath jsonPathEvaluator = res.jsonPath();
		String id = jsonPathEvaluator.get("data.findAll { data -> data.title == 'Testing Post'}.id").toString();
		id = id.replaceAll("\\[", "").replaceAll("\\]","");
		return id;
	}
}
