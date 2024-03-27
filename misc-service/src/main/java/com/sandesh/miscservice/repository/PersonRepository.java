package com.sandesh.miscservice.repository;

import com.sandesh.miscservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
