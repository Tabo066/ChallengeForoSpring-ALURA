package com.aluracursos.foro.domain.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);
    Optional<Topic> findById(Long id);

    List<Topic> findAllByActivoTrue();

    @Query("""
    SELECT t FROM Topic t
    WHERE (:courseName IS NULL OR t.course.name = :courseName)
      AND (:year IS NULL OR YEAR(t.creationDate) = :year)
    ORDER BY t.creationDate ASC
""")
    List<Topic> findByCourseNameAndYear(
            @Param("courseName") String courseName,
            @Param("year") Integer year
    );


}
