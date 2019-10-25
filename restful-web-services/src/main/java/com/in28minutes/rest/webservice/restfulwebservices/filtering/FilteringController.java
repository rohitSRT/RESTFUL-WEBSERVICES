package com.in28minutes.rest.webservice.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue retrieveBean()
	{
		SomeBean someBean = new SomeBean("Value1","Value2","Value3");
		return mappingJacksonBean(someBean);
		
	}

	private MappingJacksonValue mappingJacksonBean(SomeBean someBean) {
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue retieveListBean()
	{
		List<SomeBean> list = Arrays.asList(new SomeBean("field1", "field2", "field3"),new SomeBean("field11", "field22", "field33"));
		return mappinJackson(list);
		//return list;
	}

	private MappingJacksonValue mappinJackson(List<SomeBean> list) {
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
