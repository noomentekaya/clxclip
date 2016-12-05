package cl.ip.test;

import cl.ip.URLShortener;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class StepDefinition{

	 private URLShortener urlShortener;
	 private String tinyUrl;
	 private String longUrl = "www.facebook.com";
	

	    @Given("^my urlShortener exists$")
	    public void my_urlShortener_exists() throws Throwable {
	    	urlShortener = new URLShortener();
	    }

	    @When("^I call my urlShortener$")
	    public void i_call_my_urlShortener() throws Throwable {
	    	urlShortener = new URLShortener();
	    	tinyUrl = urlShortener.shortenURL(longUrl);
	    	//urlShortener.shortenURL("www.blabla.com");
	    }

	    @Then("^it should have been a not null$")
	    public void it_should_have_been_a_not_null() throws Throwable {
	    	Assert.assertNotNull(tinyUrl);
	    }

}
