package cl.ip.test;


import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;

import cl.ip.test.UrlShortenerControllerTest;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class UrlShortenerCucumberTest extends UrlShortenerControllerTest {
	
	
	@When("^the client calls /version$")
	public void the_client_issues_GET_version() throws Throwable{
//	    executeGet("http://localhost:8080/version");
	}
	 
	@Then("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int statusCode) throws Throwable {
//	    HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
//	    assertThat("status code is incorrect : "+ 
//	    latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
	}
	 
	@And("^the client receives server version (.+)$")
	public void the_client_receives_server_version_body(String version) throws Throwable {
//	    assertThat(latestResponse.getBody(), is(version));
	}
	
}