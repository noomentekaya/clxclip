package cl.ip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.session.SessionProperties.Redis;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cl.ip.Application;
import cl.ip.RedisUtils;
import cl.ip.URLShortener;
import cl.ip.UrlServiceProxy;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceProxyTest {
	    
	    private URLShortener urlShortener;
	 
	    @Mock
	    private RedisUtils redisUtils;
	 
	    
	    @InjectMocks
	    private UrlServiceProxy urlServiceProxy;
	    
	    String extendedUrl ;
	    String tinyUrl ;
	 
	    @Before
	    public void setUp(){
	    	urlShortener = new URLShortener();
	    	extendedUrl = "www.opeth.com";
	    }
	    
	    
	    @Test
	    public void saveShortnedUrlFromExtended() {
//	 		
	    	tinyUrl = urlShortener.shortenURL(extendedUrl);
	    	//tinyUrl = urlServiceProxy.saveToRedisShortnedUrl(extendedUrl);
	    	//when(urlServiceProxy.saveToRedisShortnedUrl(anyString())).thenre
	        when(urlServiceProxy.saveToRedisShortnedUrl(anyString())).thenReturn(tinyUrl);
	        assertNotNull(tinyUrl);
	        assertEquals(tinyUrl, urlServiceProxy.saveToRedisShortnedUrl(extendedUrl));
	    	
//	        // Expected objects
//	        String name = "Test Account";
//	        Account accountToSave = new Account(name);
//	        long accountId = 12345;
//	        Account persistedAccount = new Account(name);
//	        persistedAccount.setId(accountId);
//	 
//	        // Mockito expectations                            
        //when(accountDAO.save(any(Account.class))).thenReturn(persistedAccount);
//	        doNothing().when(notificationService).notifyOfNewAccount(accountId);
//	 
//	        // Execute the method being tested     
//	        Account newAccount = accountService.createNewAccount(name);
//	 
//	        // Validation  
//	        assertNotNull(newAccount);
//	        assertEquals(accountId, newAccount.getId());
//	        assertEquals(name, newAccount.getName());
//	 
//	        verify(notificationService).notifyOfNewAccount(accountId);
//	        verify(accountDAO).save(accountToSave);
	    }
}