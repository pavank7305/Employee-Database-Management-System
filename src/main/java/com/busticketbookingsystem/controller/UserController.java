package com.busticketbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busticketbookingsystem.enums.UserRole;
import com.busticketbookingsystem.requestdto.UserRequest;
import com.busticketbookingsystem.responsedto.UserResponse;
import com.busticketbookingsystem.service.UserService;
import com.busticketbookingsystem.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user-roles/{userRole}/users")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@PathVariable UserRole userRole,
			@RequestBody @Valid UserRequest userRequest) {
		return userService.registerUser(userRole, userRequest);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId) {
		return userService.findUserById(userId);
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable int userId,
			@RequestBody UserRequest userRequest) {
		return userService.updateUser(userId, userRequest);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}

}
