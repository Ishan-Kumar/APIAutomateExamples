package com.automate.api;

import static io.restassured.RestAssured.*;
import static io.restassured.path.json.JsonPath.from;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasXPath;

import java.io.InputStream;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class apiTest 
{

	String getURL = "https://jsonplaceholder.typicode.com/posts"; 
	String getURLXML = "http://thomas-bayer.com/sqlrest/CUSTOMER/10";
	String postURL = "http://api.fonts.com/rest/json/Accounts/";

	/* 
	 * Check simple status code
	 */
	//@Test()
	public void testStatusCode()
	{
		given().
		get(getURL).
		then().
		statusCode(200);
	}

	/* 
	 * Verify code and print complete response in console
	 */
	//@Test()
	public void testLogAll()
	{
		given()
		.get(getURL).
		then()
		.statusCode(200).log().all();
	}

	/* 
	 * Verify multiple content using hamcrest library
	 */
	//@Test()
	public void testHostItemFunction()
	{
		given()
		.get(getURL).
		then()
		.body("title",hasItems("laboriosam dolor voluptates",
				"beatae soluta recusandae", "aut amet sed"));
	}

	/* 
	 * parameters and headers can be set
	 * for url. setting dummy values in this example
	 */
	//@Test()
	public void testParametersAndHeaders()
	{
		System.out.println("---***---");
		given()
		.param("key1", "value1")
		.header("headA", "value A").
		when()
		.get(getURL).
		then()
		.statusCode(200).log().all();
	}

	/* 
	 * verify xml response single content
	 */
	//@Test()
	public void testXmlResponseSingleContent()
	{
		given()
		.get(getURLXML).
		then()
		.body("CUSTOMER.ID", equalTo("10"))
		.log().all();
	}

	/* 
	 * verify xml response multiple content
	 */
	//@Test()
	public void testXmlResponseMultipleContent()
	{
		given()
		.get(getURLXML).
		then()
		.body("CUSTOMER.ID", equalTo("10"))
		.body("CUSTOMER.FIRSTNAME", equalTo("Sue"))
		.body("CUSTOMER.LASTNAME", equalTo("Fuller"))
		.body("CUSTOMER.CITY", equalTo("Dallas"));
	}

	/* 
	 * verify xml response using xpath
	 */
	//@Test()
	public void testXmlResponseXPATH()
	{
		given()
		.get("http://thomas-bayer.com/sqlrest/INVOICE/").
		then()
		.body(hasXPath("//INVOICEList/INVOICE[text()='40']", containsString("40")));
	}

	/* 
	 * Test post request
	 */
	//@Test()
	public void testPostRequest()
	{
		given()
		.headers("headA", "value A")
		.param("AppKey", "KeyValue")
		.param("AppKeytest").
		when()
		.post(postURL).
		then()
		.statusCode(400).log().all();
	}

	/* 
	 * Test get response as String 
	 * and manipulate it the way you want using regex
	 */
	//@Test()
	public void testGetResponseAsString()
	{

		String responseFromGet = get(getURL + "/10").asString();
		System.out.println("Response as String is : --> " + responseFromGet);
	}

	/* 
	 * Test get response as stream 
	 */
	//@Test()
	public void testGetResponseAsStream()
	{

		InputStream stream = get(getURL + "/10").asInputStream();
		System.out.println("length of stream is : --> " + stream.toString().length());
	}


	/* 
	 * to extract details using path
	 */
	//@Test()
	public void extractDetailsUsingPath()
	{
		String href = 
				when()
				.get("https://jsonplaceholder.typicode.com/photos/1").
				then()
				.contentType(ContentType.JSON)
				.body("albumId", equalTo(1))
				.extract()
				.path("url");

		System.out.println("extracted URL is--> " + href);

		when().get(href).then().statusCode(200);
	}

	/* 
	 * to extract response object and use as 
	 * per requirement
	 */
	//@Test()
	public void extractresponse()
	{
		Response response = 
				when()
				.get("https://jsonplaceholder.typicode.com/photos/1").
				then()
				.extract()
				.response();
		System.out.println("Content Type: " + response.contentType());
		System.out.println("Href: " + response.path("url"));
		System.out.println("Status code is " + response.statusCode());
	}

	/* 
	 * to validate content Type
	 */
	@Test()
	public void testContentType()
	{
		given()
		.get("https://jsonplaceholder.typicode.com/photos/1").
		then()
		.statusCode(200)
		.contentType(ContentType.JSON);
	}
	
	/* 
	 * This test will verify the response schema with predefined existing schema 
	 */
	@Test()
	public void testSchema()
	{
//		given()
//		.get("https://jsonplaceholder.typicode.com/photos/1").
//		then()
//		.assertThat().body(matchesJsonSchemaInClassPath("give ur json file path"));
	}
	
	
	
	
}// end of class 
