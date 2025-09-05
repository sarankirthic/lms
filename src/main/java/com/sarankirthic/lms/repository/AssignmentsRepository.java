package com.sarankirthic.lms.repository;

import com.sarankirthic.lms.model.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentsRepository extends JpaRepository<Assignments, Long> {
}
