package jkavan.todo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Size(min=3, max=40)
  @NotBlank(message = "Task description is mandatory")
  private String title;

  @Column(columnDefinition = "DATETIME(3)")
  private LocalDateTime completed;

  @ManyToOne
  @JoinColumn(name = "project")
  @NotNull
  private Project project;

  public Task() {
  }

  public Task(String title, Project project) {
    super();
    this.title = title;
    this.project = project;
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

  public LocalDateTime getCompleted() {
    return completed;
  }

  public void setCompleted() {
    if (this.completed == null)
      this.completed = LocalDateTime.now();
  }

  public void setIncomplete() {
    this.completed = null;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", title=" + title + ", completed=" + completed + "]";
  }

}
