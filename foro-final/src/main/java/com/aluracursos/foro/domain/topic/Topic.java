package com.aluracursos.foro.domain.topic;

import com.aluracursos.foro.domain.course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.aluracursos.foro.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TopicStatus status = TopicStatus.OPEN;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @Column(nullable = false)
    private boolean activo = true;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Topic(String title, String message, TopicStatus status, User author, Course course) {
        this.title = title;
        this.message = message;
        this.status = status;
        this.author = author;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}