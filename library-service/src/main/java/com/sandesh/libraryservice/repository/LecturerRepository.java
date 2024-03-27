package com.sandesh.libraryservice.repository;

import com.sandesh.libraryservice.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
