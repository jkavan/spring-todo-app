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

import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;

@RestController
public class ProjectRestController {

  @Autowired
  private ProjectRepository repository;
  
  @GetMapping("/api/projects")
  public Iterable<Project> getProjects() {
    return repository.findAll();
  }

  @GetMapping("/api/projects/{id}")
  Optional<Project> getProject(@PathVariable Long id) {
    return repository.findById(id);
  }
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/api/new_project")
  Project newProject(@RequestBody Project newProject) {
    return repository.save(newProject);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/api/edit_project/{id}")
  Project editProject(@RequestBody Project editedProject, @PathVariable Long id) {
    editedProject.setId(id);
    return repository.save(editedProject);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/api/delete_project/{id}")
  public Iterable<Project> deleteProject(@PathVariable Long id) {
    repository.deleteById(id);
    return repository.findAll();
  }

}
