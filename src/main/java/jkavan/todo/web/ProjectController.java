package jkavan.todo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;

@Controller
public class ProjectController {

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
  public String save(@Valid Project project, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "editproject";
    }
    repository.save(project);
    return "redirect:/tasks/" + project.getId();
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
