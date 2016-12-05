package cl.ip;

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
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public UrlResponse getExtendedUrl(@RequestBody String tinyUrl){
		String extendedUrl =urlservice.getExtendedUrlFromTinyUrl(tinyUrl);
		UrlResponse response = new UrlResponse();
		response.url = extendedUrl;
		return response;
	}
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public UrlResponse deleteUrl(@RequestBody String tinyUrl){
		String extendedUrl =urlservice.deleteURL(tinyUrl);
		UrlResponse response = new UrlResponse();
		response.url = extendedUrl;
		return response;
	}
	
}
