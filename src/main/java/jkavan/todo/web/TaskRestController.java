package jkavan.todo.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jkavan.todo.domain.Task;
import jkavan.todo.domain.TaskRepository;
import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;

@RestController
public class TaskRestController {

  @Autowired
  private TaskRepository repository;

  @Autowired
  private ProjectRepository projects;
  
  @GetMapping("/api/tasks")
  public Iterable<Task> getTasks() {
    return repository.findAll();
  }

  @GetMapping("/api/task/{id}")
  Optional<Task> getTask(@PathVariable Long id) {
    return repository.findById(id);
  }
  
  @GetMapping(value = { "/api/tasks/{project_id}" })
  public Iterable<Task> getTasksByProject(@PathVariable("project_id") Long projectId) {
    Project project = projects.findById(projectId).orElse(null);
    return repository.findByProject(project);
  }
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/api/new_task")
  Task newTask(@RequestBody Task newTask) {
    return repository.save(newTask);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/api/edit_task/{id}")
  Task editTask(@RequestBody Task editedTask, @PathVariable Long id) {
    editedTask.setId(id);
    return repository.save(editedTask);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/api/delete_task/{id}")
  public Iterable<Task> deleteTask(@PathVariable Long id) {
    repository.deleteById(id);
    return repository.findAll();
  }

  @PostMapping(value = "/api/complete_task/{id}")
  public Optional<Task> complete(@PathVariable("id") Long taskId) {
    Task task = repository.findById(taskId).orElse(null);
    task.setCompleted();
    Project project = task.getProject();
    repository.save(task);
    updateProjectStatus(project);
    return repository.findById(taskId);
  }

  @PostMapping(value = "/api/incomplete_task/{id}")
  public Optional<Task> incomplete(@PathVariable("id") Long taskId) {
    Task task = repository.findById(taskId).orElse(null);
    task.setIncomplete();
    Project project = task.getProject();
    repository.save(task);
    updateProjectStatus(project);
    return repository.findById(taskId);
  }

  public void updateProjectStatus(Project project) {
    if (repository.countByProjectAndCompletedIsNull(project) == 0 && repository.countByProject(project) > 0) {
      project.setCompleted();
    } else {
      project.setIncomplete();
    }
    projects.save(project);
  }
}
