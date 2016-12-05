package cl.ip.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import cl.ip.Application;
import cl.ip.RedisUtils;
import cl.ip.URLShortener;
import cl.ip.UrlServiceProxy;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UrlShortenerControllerTest {


     MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

     MockMvc mockMvc;

    HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    RedisUtils redisUtils;
    
    URLShortener urlShortener;
    
    @Autowired
    UrlServiceProxy urlServiceProxy;
    
    String expandedUrl ;
    String tinyUrl;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.urlShortener = new URLShortener();
    }

    @Test
    public void urlShortnedTestOkayPost() throws Exception {
    	this.expandedUrl =  "http://this.is.happy.test";
        mockMvc.perform(post("/clip")
                .content(expandedUrl)
                .contentType(contentType))
                .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void urlShortnedTestOkayGet() throws Exception {
    	tinyUrl = "www.cl.ip/SomEthing";
    	  mockMvc.perform(get("/clip")
                  .content(tinyUrl)
                  .contentType(contentType))
                  .andExpect(status().is2xxSuccessful());
    }
    
    @Test 
    public void urlTestOkayDelete() throws Exception {
    	tinyUrl = "www.cl.ip/SomEthing";
  	  mockMvc.perform(delete("/clip")
                .content(tinyUrl)
                .contentType(contentType))
                .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void urlTestNotOkayPut() throws Exception {
    	tinyUrl = "www.cl.ip/SomEthing";
    	  mockMvc.perform(put("/clip")
                  .content(tinyUrl)
                  .contentType(contentType))
                  .andExpect(status().is4xxClientError());
    }
    @Test
    public void urlTestNotOkayPostWithoutBody() throws Exception {
    	mockMvc.perform(post("/clip")
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void urlTestNotOkayDeleteWithoutBody() throws Exception {
    	mockMvc.perform(delete("/clip")
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void urlTestNotOkayGetWithoutBody() throws Exception {
    	mockMvc.perform(get("/clip")
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void urlTestNotOkayPostWithInteger () throws Exception {
    	mockMvc.perform(post("/clip")
                .requestAttr("something", "somehow")
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void urlTestSameUrlIsReturnedWithPost () throws Exception {
    	expandedUrl = "www.opeth.com";
    	tinyUrl = urlServiceProxy.saveToRedisShortnedUrl(expandedUrl);
    	mockMvc.perform(post("/clip")
                .content(expandedUrl)
                .contentType(contentType))
                .andExpect(jsonPath("$.url", is(tinyUrl)));
    }
       
}