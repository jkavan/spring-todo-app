package jkavan.todo.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  @Column(columnDefinition = "DATETIME(3)")
  private LocalDateTime completed;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
  private List<Task> tasks;

  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCompleted() {
    return completed;
  }

  public void setCompleted() {
    this.completed = LocalDateTime.now();
  }

  public void setIncomplete() {
    this.completed = null;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public String toString() {
    return "Project [id=" + id + ", name=" + name + "]";
  }
}