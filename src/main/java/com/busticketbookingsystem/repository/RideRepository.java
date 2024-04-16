package com.busticketbookingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.busticketbookingsystem.entity.Bus;
import com.busticketbookingsystem.entity.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {

	@Query("select r from Ride r where r.bus =?1 and r.startDateTime between ?2 and ?3 and r.endDateTime between ?2 and ?3")
	public Optional<Ride> findRideByBusAndStartTimeAndEndTime(Bus bus, LocalDateTime startTime, LocalDateTime endTime);

	@Query("select r from Ride r where r.bus =?1 and r.startDateTime between ?2 and ?3")
	public Optional<Ride> findRideByBusAndStartTimeBetweenGivenTime(Bus bus, LocalDateTime startTime,
			LocalDateTime endTime);

	@Query("from Ride r where r.startDateTime =?1")
	public List<Ride> findRideByStartTime(LocalDateTime currentTime);

	@Query("from Ride r where r.endDateTime =?1")
	public List<Ride> findRideByEndTime(LocalDateTime currentTime);

}
