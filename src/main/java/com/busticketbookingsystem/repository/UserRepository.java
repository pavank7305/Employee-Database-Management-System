package com.busticketbookingsystem.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.busticketbookingsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("from User where isDeleted = true and deletedDateTime <?1")
	public Optional<List<User>> findDeletedUser(Date dateTime);

}
