package com.aluracursos.foro.controller;

import com.aluracursos.foro.domain.course.Course;
import com.aluracursos.foro.domain.course.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.aluracursos.foro.domain.topic.*;
import com.aluracursos.foro.domain.user.User;
import com.aluracursos.foro.domain.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createTopic(@RequestBody @Valid DatosTopic request) {
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Topic topic = new Topic(
                request.title(),
                request.message(),
                TopicStatus.OPEN,
                author,
                course
        );

        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }
    @GetMapping
    public ResponseEntity<List<TopicResponse>> listAll() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicResponse> response = topics.stream()
                .map(TopicResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<TopicResponse>> listPaginated(
            @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TopicResponse> page = topicRepository.findAll(pageable)
                .map(TopicResponse::from);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<TopicResponse>> filter(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) Integer year
    ) {
        List<Topic> topics = topicRepository.findByCourseNameAndYear(courseName, year);
        List<TopicResponse> response = topics.stream()
                .map(TopicResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<TopicDetailResponse> getTopicDetail(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    var dto = new TopicDetailResponse(
                            topic.getTitle(),
                            topic.getMessage(),
                            topic.getCreationDate(),
                            topic.getStatus(),
                            topic.getAuthor().getUsername(),
                            topic.getCourse().getName()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateTopic(@PathVariable Long id, @RequestBody @Valid UpdateTopicRequest request) {
        if (!topicRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found");
        }

        boolean existsDuplicate = topicRepository.findAll().stream()
                .anyMatch(t -> !t.getId().equals(id)
                        && t.getTitle().equalsIgnoreCase(request.title())
                        && t.getMessage().equalsIgnoreCase(request.message()));
        if (existsDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate topic with same title and message");
        }

        Topic topic = topicRepository.findById(id).get();
        topic.setTitle(request.title());
        topic.setMessage(request.message());
        topicRepository.save(topic);
        return ResponseEntity.ok("Topic updated successfully");
    }
    // DELETE FÍSICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        if (!topicRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found");
        }
        topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE LÓGICO
    @DeleteMapping("/{id}/logic")
    @Transactional
    public ResponseEntity<?> deleteTopicLogically(@PathVariable Long id) {
        var optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found");
        }
        Topic topic = optionalTopic.get();
        topic.setActivo(false);
        topic.setStatus(TopicStatus.CLOSED);
        topicRepository.save(topic);

        return ResponseEntity.noContent().build();
    }
}