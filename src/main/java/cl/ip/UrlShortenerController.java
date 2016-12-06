package cl.ip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/clip")
public class UrlShortenerController {
	@Autowired
	UrlServiceProxy urlservice;

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public UrlResponse saveExtendedUrltoTinyUrl (@RequestBody String url){
		String tinyUrl =urlservice.saveToRedisShortnedUrl(url);
		UrlResponse response = new UrlResponse();
		response.url = tinyUrl;
		return response;
	} 
	@RequestMapping(method=RequestMethod.PUT) //for design purpose i chosen to use PUT for this instead of a get (see angular code especially http methods)
	@ResponseBody
	public UrlResponse getExtendedUrl(@RequestBody String tinyUrl) throws Exception {
		String extendedUrl =urlservice.getExtendedUrlFromTinyUrl(tinyUrl);
		UrlResponse response = new UrlResponse();
		if (extendedUrl != null ){
			response.url = extendedUrl;
			return response;
		}else {
			throw new Exception("Short URL not found");
		}
		
	}
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public UrlResponse deleteUrl(@RequestBody String tinyUrl){
		String extendedUrl =urlservice.deleteURL(tinyUrl);
		UrlResponse response = new UrlResponse();
		response.url = extendedUrl;
		return response;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/all")
	@ResponseBody
	public List<UrlResponse> getAllUrls(){
		List<UrlResponse> urls = urlservice.findAll();
		return urls;
	}
	
	
}
