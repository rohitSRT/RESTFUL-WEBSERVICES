package com.in28minutes.rest.webservice.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService userDaoService;
	//GET /usrs
	//retrieve all users
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
		return userDaoService.findAll();
	}
	//GET /users/{id}
	//retirve user(int id)
	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id)
	{
		User user = userDaoService.findOne(id);
		if (user==null)
		{
			throw new UserNotFoundException("id "+ id);
		}
		//"all-users" , SERVER_PATH +/users
		//retriver users
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	//delete request
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		User user = userDaoService.deleteById(id);
		if (user==null)
		{
			throw new UserNotFoundException("id "+ id);
		}
	}
	//input - details of user
	//CREATED and returned the created URI
	
	@PostMapping("/users")
	public ResponseEntity createUser(@Valid @RequestBody User user)
	{
		User savedUser = userDaoService.save(user);
		//CREATED
		// users/{id} savedUser.getId;
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}
