package com.busticketbookingsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busticketbookingsystem.entity.Bus;
import com.busticketbookingsystem.entity.Ride;
import com.busticketbookingsystem.entity.Ticket;
import com.busticketbookingsystem.enums.RideStatus;
import com.busticketbookingsystem.enums.TicketStatus;
import com.busticketbookingsystem.exception.BusNotFoundByIdException;
import com.busticketbookingsystem.exception.DestinationCannotBeAlteredException;
import com.busticketbookingsystem.exception.RideCannotBeCreatedForStartDateTime;
import com.busticketbookingsystem.exception.RideDeletedException;
import com.busticketbookingsystem.exception.RideNotFoundByIdException;
import com.busticketbookingsystem.repository.BusRepository;
import com.busticketbookingsystem.repository.RideRepository;
import com.busticketbookingsystem.repository.TicketRepository;
import com.busticketbookingsystem.requestdto.RideRequest;
import com.busticketbookingsystem.responsedto.RideResponse;
import com.busticketbookingsystem.util.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class RideService {

	@Autowired
	private RideRepository rideRepo;

	@Autowired
	private BusRepository busRepo;

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private ResponseStructure<RideResponse> responseStructure;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<RideResponse>> saveRide(int busId, @Valid RideRequest rideRequest) {

		Ride ride = mapper.map(rideRequest, Ride.class);
		Bus bus = busRepo.findById(busId).orElseThrow(() -> new BusNotFoundByIdException("Failed to save the ride"));

		if (rideRequest.getEndDateTime().isBefore(LocalDateTime.now())) {
			throw new RideCannotBeCreatedForStartDateTime("Ride start time is after end time");
		}
		LocalDateTime updatedStartTime = rideRequest.getStartDateTime().minusMinutes(10l);
		LocalDateTime updatedEndTime = rideRequest.getEndDateTime().plusMinutes(10l);
		Optional<Ride> optional = rideRepo.findRideByBusAndStartTimeAndEndTime(bus, updatedStartTime, updatedEndTime);
		if (optional.isPresent()) {
			throw new RideCannotBeCreatedForStartDateTime("Ride cannot be created for the requested start time");
		}
		Optional<Ride> optional2 = rideRepo.findRideByBusAndStartTimeBetweenGivenTime(bus, LocalDateTime.now(),
				LocalDateTime.now().plusHours(24l));
		if (optional2.isPresent()) {
			throw new RideCannotBeCreatedForStartDateTime("Ride cannot be created for the requested start time");
		}
		ride.setBus(bus);
		ride.setRideStatus(RideStatus.BOOKED);
		ride = rideRepo.save(ride);
		for (int i = 0; i < ride.getNoOfTickets(); i++) {
			Ticket ticket = new Ticket();
			ticket.setTicketStatus(TicketStatus.BOOKED);
			ticket.setRide(ride);
			ticketRepo.save(ticket);
		}
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Ride Added Successfully");
		responseStructure.setData(mapper.map(ride, RideResponse.class));
		return new ResponseEntity<ResponseStructure<RideResponse>>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<RideResponse>> updateRide(int rideId, @Valid RideRequest rideRequest) {

		Ride ride = rideRepo.findById(rideId)
				.orElseThrow(() -> new RideNotFoundByIdException("Failed to update the ride"));
		if (!ride.getDestination().equals(rideRequest.getDestination())) {
			throw new DestinationCannotBeAlteredException("Failed to update the ride");
		}
		ride = mapper.map(rideRequest, Ride.class);
		ride.setRideId(rideId);
		ride = rideRepo.save(ride);
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("Ride Updated Successfully");
		responseStructure.setData(mapper.map(ride, RideResponse.class));
		return new ResponseEntity<ResponseStructure<RideResponse>>(responseStructure, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<RideResponse>> findRideById(int rideId) {

		Ride ride = rideRepo.findById(rideId)
				.orElseThrow(() -> new RideNotFoundByIdException("Failed to fetch the ride"));
		if (ride.getRideStatus() != RideStatus.CANCELLED) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("Ride Found Successfully");
			responseStructure.setData(mapper.map(ride, RideResponse.class));
			return new ResponseEntity<ResponseStructure<RideResponse>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new RideDeletedException("Failed to fetch the ride");
		}

	}

	public ResponseEntity<ResponseStructure<RideResponse>> cancelRide(int rideId) {

		Ride ride = rideRepo.findById(rideId)
				.orElseThrow(() -> new RideNotFoundByIdException("Failed to cancel the ride"));
		ride.setRideStatus(RideStatus.CANCELLED);
		ride = rideRepo.save(ride);
		for (Ticket ticket : ticketRepo.getTickets(ride)) {
			ticket.setTicketStatus(TicketStatus.CANCELLED);
			ticketRepo.save(ticket);
		}
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("Ride Cancelled Successfully");
		responseStructure.setData(mapper.map(ride, RideResponse.class));
		return new ResponseEntity<ResponseStructure<RideResponse>>(responseStructure, HttpStatus.OK);

	}

}
