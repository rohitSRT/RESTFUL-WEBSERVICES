package com.in28minutes.rest.webservice.restfulwebservices.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="All details about the user")
@Entity 
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	@Size(min=3,message="At least 3 characters should be there in the name")
	@ApiModelProperty(notes="At least 3 characters should be there in the name")
	private String name;
	@Past(message="date must be smaller than today's date ")
	@ApiModelProperty(notes="date must be smaller than today's date ")
	private Date birthDate;
	
	@OneToMany(mappedBy="user")
	private List<Post> posts;
	public User()
	{
		
	}
	public User(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, birthDate=%s]", id, name, birthDate);
	}
	
}
