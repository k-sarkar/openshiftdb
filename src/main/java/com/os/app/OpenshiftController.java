package com.os.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenshiftController {
	
	@Autowired
	private UsersRepository userRepo;

	static Integer healthCounter = 0;
	
	@Value("${message:default message}")
	private String message;
	
	@Value("${config_message:default config message}")
	private String configMessage;
	
	@Value("${secret_message:default secret message}")
	private String secretMessage;

	@GetMapping(path = "/users")
	public ResponseEntity<List<Users>>  getUser() {
		List<Users> result= (List<Users>) userRepo.findAll();
		
		return new ResponseEntity<List<Users>>(result, HttpStatus.OK);
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<Users> addUser(@RequestBody Users user) {
		//user.addAddresses(user.getAddresses());
		Users savedUser = userRepo.save(user);
		
		return new ResponseEntity<Users>(savedUser, HttpStatus.CREATED);
	}

	
	@GetMapping("/message")
	public String getGetMessage() {
		return message;
	}
	
	@GetMapping("/config/message")
	public String getGetConfigMessage() {
		return configMessage;
	}
	
	
	@GetMapping("/secret/message")
	public String getGetSecretMessage() {
		return secretMessage;
	}
	
	@GetMapping("/health")
	public ResponseEntity getHelathCheck() { 

		if(healthCounter%4==0) {
			healthCounter ++;
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
		else {
			healthCounter ++;
			return new ResponseEntity(HttpStatus.OK);
		}
		
		

	}
	
	@GetMapping("/readiness/{value}")
	public ResponseEntity<String> getReadinessCheck(@PathVariable String value) {
		
		if(value.equalsIgnoreCase("Y"))
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);

	}
	
	
}
