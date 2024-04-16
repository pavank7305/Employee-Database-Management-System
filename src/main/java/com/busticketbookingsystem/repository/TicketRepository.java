package com.busticketbookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.busticketbookingsystem.entity.Ride;
import com.busticketbookingsystem.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query("select t from Ticket t where t.ride =?1")
	public List<Ticket> getTickets(Ride ride);

}
