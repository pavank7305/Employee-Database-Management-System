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

import com.busticketbookingsystem.requestdto.BusRequest;
import com.busticketbookingsystem.responsedto.BusResponse;
import com.busticketbookingsystem.service.BusService;
import com.busticketbookingsystem.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class BusController {

	@Autowired
	private BusService busService;

	@PostMapping("/users/{userId}/buses")
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(@PathVariable int userId,
			@RequestBody @Valid BusRequest busRequest) {
		return busService.saveBus(userId, busRequest);
	}

	@PutMapping("/buses/{busId}")
	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(@RequestBody @Valid BusRequest busRequest,
			@PathVariable int busId) {
		return busService.updateBus(busId, busRequest);
	}

	@GetMapping("/buses/{busId}")
	public ResponseEntity<ResponseStructure<BusResponse>> findBusById(@PathVariable int busId) {
		return busService.findBusById(busId);
	}

	@DeleteMapping("/buses/{busId}")
	public ResponseEntity<ResponseStructure<BusResponse>> deleteBus(@PathVariable int busId) {
		return busService.deleteBus(busId);
	}

}
