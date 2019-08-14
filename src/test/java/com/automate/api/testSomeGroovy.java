package com.automate.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import static io.restassured.path.json.JsonPath.*;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;

public class testSomeGroovy 
{

	/* 
	 * Verify if some expected name present in response or not
	 */
	//@Test()
	public void testPresenceofElements()
	{
		given()
		.get("https://jsonplaceholder.typicode.com/posts").
		then()
		.body("title", hasItems("qui est esse", "nesciunt quas odio")).log().all();
	}
	
	/* 
	 * RestAssured framework is implemented in groovy and
	 * hence groovy advantages can be taken as here we are adding length of
	 * all the title coming in response
	 */
	//@Test()
	public void testLengthofResponse()
	{
		given()
		.get("https://jsonplaceholder.typicode.com/posts").
		then()
		.body("title*.length().sum()", greaterThan(3953));
	}
	
	/* 
	 * to get all Attributes as 
	 * List and verify the value
	 */
	//@Test()
	public void testGetResponseAsList()
	{
		String reponse = get("https://jsonplaceholder.typicode.com/posts").asString();
		List<String> ls = from(reponse).getList("title");
		
		System.out.println("List Size" + ls.size());
		
		//Verify values in List
		for(String title: ls) {
			if(title.equals("nesciunt quas odio"))
				System.out.println("My title exists");
		}
	}
	
	/* 
	 * to get response as list and apply some conditions to it
	 * the groovy has an implicit variables called 'it' which 
	 * represents the current item in the list
	 * 
	 */
	//@Test()
	public void testConditionsOnList()
	{
		String reponse = get("https://jsonplaceholder.typicode.com/posts").asString();
		// all the title bigger than 60 will be returned as a list
		List<String> ls = from(reponse).getList("findAll { it.title.length() > 60 }.title");
		System.out.println(ls);
	}
	
	/* 
	 * Extract details as String and fetching further details
	 * w/o using json path
	 */
	//@Test()
	public void testwithoutJsonPath()
	{
		String reponse = get("https://jsonplaceholder.typicode.com/photos").asString();
		// all the title bigger than 60 will be returned as a list
		List<String> ls = from(reponse).getList("id");
		System.out.println(ls.size());
	}
	
	/* 
	 * Extract details as String and fetching further details
	 * using json path
	 */
	@Test()
	public void testwithJsonPath()
	{
		String response = get("https://jsonplaceholder.typicode.com/photos").asString();
		// all the title bigger than 60 will be returned as a list
		List<String> ls = from(response).getList("id");
		
		JsonPath jsonpath = new JsonPath(response).setRoot("albumId");
		
		List<String> ls1 = jsonpath.get("");
		System.out.println(ls1.size());
	}
	
	
}
