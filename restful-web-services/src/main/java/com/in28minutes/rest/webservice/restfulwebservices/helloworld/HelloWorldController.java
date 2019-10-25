package com.in28minutes.rest.webservice.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	//GET
	//uri - /hello-world
	
	@RequestMapping(method=RequestMethod.GET,path = "/hello-world",produces = {MediaType.APPLICATION_JSON_VALUE})
	public String helloWorld()
	{
		return "Hello World";
	}
	@GetMapping(path = "/hello-world-get")
	public String helloWorldGet()
	{
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-bean",produces = {MediaType.APPLICATION_JSON_VALUE})
	public HelloWorldBean helloWorldBean()
	{
		return new HelloWorldBean("Hello World");
	}
	//hello-world/path-variable/in28minutes
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable( @PathVariable String name)
	{
		return new HelloWorldBean(String.format("Hello World, %s",name));
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized(
			@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}

	
}
