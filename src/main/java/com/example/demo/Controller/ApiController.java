package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.User;
import com.example.demo.Repo.UserRepo;


@RestController
public class ApiController {
	
	@Autowired // handle dependency injections
	private UserRepo userRepo; 
	@GetMapping(value = "/") //map to main webpage
	public String getPage() {
		return "Welcome";
	}
	
	@GetMapping(value = "/users") //map to another webpage with /users
	//will be an empty array if there are no users
	//will be used by postman
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	
	@PostMapping(value="/save") //saving to database
	public String saveUser(@RequestBody User user) { 
		//the user object will be in json
		//we have no interface to submit this user object so we can use postman
		userRepo.save(user);
		return "User saved";
	}
	
	@PutMapping(value = "update/{id}")
	public String updateUser (@PathVariable long id, @RequestBody User user) {
		//find the user by id in the database then retrieve it
		User updatedUser = userRepo.findById(id).get();
		updatedUser.setFirstName(user.getFirstName());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setOccupation(user.getOccupation());
		updatedUser.setAge(user.getAge()); 
		userRepo.save(updatedUser);
		return "Updated user";
		
	}

	
	@DeleteMapping(value = "/delete/{id}")
		public String deleteUser(@PathVariable long id) {
			User deleteUser = userRepo.findById(id).get();
			userRepo.delete(deleteUser);
			return "User deleted with id:" + id;
			
		}
	}
	


	
