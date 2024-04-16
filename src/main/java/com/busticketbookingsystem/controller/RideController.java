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

import com.busticketbookingsystem.requestdto.RideRequest;
import com.busticketbookingsystem.responsedto.RideResponse;
import com.busticketbookingsystem.service.RideService;
import com.busticketbookingsystem.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class RideController {

	@Autowired
	private RideService rideService;

	@PostMapping("buses/{busId}/rides")
	public ResponseEntity<ResponseStructure<RideResponse>> saveRide(@PathVariable int busId,
			@RequestBody @Valid RideRequest rideRequest) {
		return rideService.saveRide(busId, rideRequest);
	}

	@PutMapping("/rides/{rideId}")
	public ResponseEntity<ResponseStructure<RideResponse>> updateRide(@PathVariable int rideId,
			@RequestBody RideRequest rideRequest) {
		return rideService.updateRide(rideId, rideRequest);
	}

	@GetMapping("/rides/{rideId}")
	public ResponseEntity<ResponseStructure<RideResponse>> findRideById(@PathVariable int rideId) {
		return rideService.findRideById(rideId);
	}

	@DeleteMapping("/rides/{rideId}")
	public ResponseEntity<ResponseStructure<RideResponse>> cancelRide(@PathVariable int rideId) {
		return rideService.cancelRide(rideId);
	}

}
