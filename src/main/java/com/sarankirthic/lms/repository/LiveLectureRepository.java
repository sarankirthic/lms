package com.sarankirthic.lms.repository;

import com.sarankirthic.lms.model.LiveLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveLectureRepository extends JpaRepository<LiveLecture, Long> {
}
