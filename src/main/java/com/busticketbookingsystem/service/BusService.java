package com.busticketbookingsystem.service;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busticketbookingsystem.entity.Bus;
import com.busticketbookingsystem.entity.Seat;
import com.busticketbookingsystem.entity.User;
import com.busticketbookingsystem.enums.UserRole;
import com.busticketbookingsystem.exception.BusDeletedException;
import com.busticketbookingsystem.exception.BusNotFoundByIdException;
import com.busticketbookingsystem.exception.SeatNotFoundBySeatNoAndBus;
import com.busticketbookingsystem.exception.UnauthorizedAccessException;
import com.busticketbookingsystem.exception.UserNotFoundByIdException;
import com.busticketbookingsystem.repository.BusRepository;
import com.busticketbookingsystem.repository.SeatRepository;
import com.busticketbookingsystem.repository.UserRepository;
import com.busticketbookingsystem.requestdto.BusRequest;
import com.busticketbookingsystem.responsedto.BusResponse;
import com.busticketbookingsystem.util.ResponseStructure;

@Service
public class BusService {

	@Autowired
	private BusRepository busRepo;

	@Autowired
	private SeatRepository seatRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ResponseStructure<BusResponse> responseStructure;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(int userId, BusRequest busRequest) {
		Bus bus = mapper.map(busRequest, Bus.class);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("Failed to create a bus data."));
		bus.setUser(user);
		bus = busRepo.save(bus);
		for (int i = 0; i < bus.getNoOfSeats(); i++) {
			Seat seat = new Seat();
			seat.setSeatNo(i + 1);
			seat.setBus(bus);
			seatRepo.save(seat);
		}
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Bus Added Successfully");
		responseStructure.setData(mapper.map(bus, BusResponse.class));
		return new ResponseEntity<ResponseStructure<BusResponse>>(responseStructure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(int busId, BusRequest busRequest) {
		Bus bus = busRepo.findById(busId)
				.orElseThrow(() -> new BusNotFoundByIdException("Failed to update the Bus data!"));
		User user = bus.getUser();
		if (user.getUserRole().equals(UserRole.AGENT)) {
			int countofSeats = seatRepo.countofSeats(bus);
			int lastSeatNo = countofSeats;
			if (busRequest.getNoOfSeats() > countofSeats) {
				for (int i = lastSeatNo + 1; i <= busRequest.getNoOfSeats(); i++) {
					Seat seat = new Seat();
					seat.setSeatNo(i);
					seat.setBus(bus);
					seatRepo.save(seat);
				}
			} else if (busRequest.getNoOfSeats() < countofSeats) {
				for (int i = lastSeatNo; i > busRequest.getNoOfSeats(); i--) {
					Optional<Seat> optional = seatRepo.getSeat(i, bus);
					if (optional.isPresent()) {
						Seat seat = optional.get();
						seatRepo.delete(seat);
					} else {
						throw new SeatNotFoundBySeatNoAndBus("Failed to delete the seat");
					}
				}
			}
			bus = mapper.map(busRequest, Bus.class);
			bus.setBusId(busId);
			bus.setUser(user);
			bus = busRepo.save(bus);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Bus Updated Successfully");
			responseStructure.setData(mapper.map(bus, BusResponse.class));
			return new ResponseEntity<ResponseStructure<BusResponse>>(responseStructure, HttpStatus.OK);
		} else {
			throw new UnauthorizedAccessException("Failed to update the bus data.");
		}

	}

	public ResponseEntity<ResponseStructure<BusResponse>> findBusById(int busId) {
		Bus bus = busRepo.findById(busId).orElseThrow(() -> new BusNotFoundByIdException("Failed to fetch the bus"));
		if (!bus.isDeleted()) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("Bus Found Successfully");
			responseStructure.setData(mapper.map(bus, BusResponse.class));
			return new ResponseEntity<ResponseStructure<BusResponse>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new BusDeletedException("Failed to fetch the bus");
		}

	}

	public ResponseEntity<ResponseStructure<BusResponse>> deleteBus(int busId) {

		Bus bus = busRepo.findById(busId).orElseThrow(() -> new BusNotFoundByIdException("Failed to delete the bus"));
		User user = bus.getUser();
		if (user.getUserRole().equals(UserRole.AGENT)) {
			bus.setDeleted(true);
			bus.setDeletedDateTime(new Date(System.currentTimeMillis()));
			bus = busRepo.save(bus);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Bus Deleted Successfully");
			responseStructure.setData(mapper.map(bus, BusResponse.class));
			return new ResponseEntity<ResponseStructure<BusResponse>>(responseStructure, HttpStatus.OK);
		} else {
			throw new UnauthorizedAccessException("Failed to delete the bus data.");
		}

	}

}
