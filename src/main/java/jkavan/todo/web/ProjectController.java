package jkavan.todo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;
import jkavan.todo.domain.Task;
import jkavan.todo.domain.TaskRepository;

@Controller
public class ProjectController {

  @Autowired
  private TaskRepository tasks;

  @Autowired
  private ProjectRepository repository;

  @GetMapping(value = { "/", "/projects" })
  public String projects(Model model) {
    model.addAttribute("projects", repository.findAll());
    return "projects";
  }

  @GetMapping(value = { "/manageprojects" })
  public String manageProjects(Model model) {
    model.addAttribute("project", new Project());
    model.addAttribute("projects", repository.findAll());
    return "manageprojects";
  }
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping(value = "/addproject")
  public String addProject(Model model) {
    model.addAttribute("project", new Project());
    return "addproject";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/saveproject")
  public String save(Project project) {
    repository.save(project);
    return "redirect:/projects";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping(value = "/editproject/{id}")
  public String editProject(@PathVariable("id") Long projectId, Model model) {
    model.addAttribute("project", repository.findById(projectId));
    return "editproject";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/deleteproject/{id}")
  public String deleteProject(@PathVariable("id") Long projectId) {
    repository.deleteById(projectId);
    return "redirect:/projects";
  }
}
