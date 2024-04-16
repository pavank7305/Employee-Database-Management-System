package com.busticketbookingsystem.util;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.busticketbookingsystem.entity.Ride;
import com.busticketbookingsystem.entity.Ticket;
import com.busticketbookingsystem.entity.User;
import com.busticketbookingsystem.enums.RideStatus;
import com.busticketbookingsystem.enums.TicketStatus;
import com.busticketbookingsystem.repository.RideRepository;
import com.busticketbookingsystem.repository.TicketRepository;
import com.busticketbookingsystem.repository.UserRepository;

@Component
public class ScheduledCleanUpTasks {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RideRepository rideRepo;

	@Autowired
	private TicketRepository ticketRepo;

	@Scheduled(cron = "0 * * * * *")
	public void deleteUsers() {
		Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 90L));
		Optional<List<User>> optional = userRepo.findDeletedUser(date);
		if (optional.isPresent()) {
			List<User> users = optional.get();
			users.forEach(System.out::print);
			if (!users.isEmpty()) {
				userRepo.deleteAll(users);
			}
		}

	}

	@Scheduled(fixedDelay = (1000 * 60))
	public void updateStatusOfStartedRides() {
		List<Ride> rides = rideRepo.findRideByStartTime(LocalDateTime.now());
		for (Ride ride : rides) {
			List<Ticket> tickets = ticketRepo.getTickets(ride);
			for (Ticket ticket : tickets) {
				ticket.setTicketStatus(TicketStatus.ACTIVE);
				ticketRepo.save(ticket);
			}
			ride.setRideStatus(RideStatus.STARTED);
			rideRepo.save(ride);
		}
	}

	@Scheduled(fixedDelay = (1000 * 60))
	public void updateStatusOfCompletedRides() {
		List<Ride> rides = rideRepo.findRideByEndTime(LocalDateTime.now());
		for (Ride ride : rides) {
			List<Ticket> tickets = ticketRepo.getTickets(ride);
			for (Ticket ticket : tickets) {
				ticket.setTicketStatus(TicketStatus.EXPIRED);
				ticketRepo.save(ticket);
			}
			ride.setRideStatus(RideStatus.COMPLETED);
			rideRepo.save(ride);
		}
	}

}
