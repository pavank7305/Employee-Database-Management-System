package com.busticketbookingsystem.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busticketbookingsystem.entity.User;
import com.busticketbookingsystem.enums.UserRole;
import com.busticketbookingsystem.exception.UserNotFoundByIdException;
import com.busticketbookingsystem.repository.UserRepository;
import com.busticketbookingsystem.requestdto.UserRequest;
import com.busticketbookingsystem.responsedto.UserResponse;
import com.busticketbookingsystem.util.ResponseStructure;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ResponseStructure<UserResponse> responseStructure;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRole userRole, UserRequest userRequest) {

		User user = mapper.map(userRequest, User.class);
		user.setUserRole(userRole);
		user = userRepo.save(user);
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("User Added Successfully");
		responseStructure.setData(mapper.map(user, UserResponse.class));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userRequest) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("Failed to update the User"));
		user = mapper.map(userRequest, User.class);
		user.setUserId(userId);
		user = userRepo.save(user);
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("User Updated Successfully");
		responseStructure.setData(mapper.map(user, UserResponse.class));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("Failed to fetch the user"));
		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("User Found Successfully");
		responseStructure.setData(mapper.map(user, UserResponse.class));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.FOUND);

	}

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("Failed to delete the user"));
		user.setDeleted(true);
		user.setDeletedDateTime(new Date(System.currentTimeMillis()));
		user = userRepo.save(user);
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("User Deleted Successfully");
		responseStructure.setData(mapper.map(user, UserResponse.class));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);

	}

}
