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
public class UserJPAResource {

	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postrepository;
	//GET /usrs
	//retrieve all users
	
	@GetMapping("/JPA/users")
	public List<User> retrieveAllUsers()
	{
		return userRepository.findAll();
	}
	//GET /users/{id}
	//retirve user(int id)
	@GetMapping("/JPA/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id)
	{
		User user = userRepository.findOne(id);
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
	@DeleteMapping("/JPA/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		userRepository.delete(id);
		/*if (user==null)
		{
			throw new UserNotFoundException("id "+ id);
		}*/
	}
	//input - details of user
	//CREATED and returned the created URI
	
	@PostMapping("/JPA/users")
	public ResponseEntity createUser(@Valid @RequestBody User user)
	{
		User savedUser = userRepository.save(user);
		//CREATED
		// users/{id} savedUser.getId;
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/JPA/users/{id}/posts")
	public List<Post> retrieveAllUserPosts(@PathVariable int id){
		User user = userRepository.findOne(id);
		if(user==null)
		{
			throw new UserNotFoundException("id-"+id);
		}
		return user.getPosts();
		
	}
	@PostMapping("/JPA/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post)
	{
		User user = userRepository.findOne(id);
		
		if(user==null)
		{
			throw new UserNotFoundException("id-"+id);
		}
		post.setUser(user);
		postrepository.save(post);
		//CREATED
		// users/{id} savedUser.getId;
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}
