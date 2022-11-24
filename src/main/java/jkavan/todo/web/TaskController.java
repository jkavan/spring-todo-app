package jkavan.todo.web;

import java.util.List;

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


import jkavan.todo.domain.Task;
import jkavan.todo.domain.TaskRepository;
import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;

@Controller
public class TaskController {

  @Autowired
  private TaskRepository repository;

  @Autowired
  private ProjectRepository projects;

  @GetMapping(value = "/tasks")
  public String tasks(Model model) {
    model.addAttribute("tasks", repository.findAll());
    model.addAttribute("projects", projects.findAll());
    return "tasks";
  }

  @GetMapping(value = { "/tasks/{project_id}" })
  public String tasksByProject(@PathVariable("project_id") Long projectId, Model model) {
    Project project = projects.findById(projectId).orElse(null);
    List<Task> tasks = repository.findByProject(project);
    model.addAttribute("task", new Task());
    model.addAttribute("project", project);
    model.addAttribute("project_id", projectId);
    model.addAttribute("tasks", tasks);
    model.addAttribute("projects", projects.findAll());
    return "tasks";
  }
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping(value = "/addtask")
  public String addTask(Model model) {
    model.addAttribute("task", new Task());
    model.addAttribute("projects", projects.findAll());
    return "addtask";
  }

  @RequestMapping(value = "/complete/{id}")
  public String complete(@PathVariable("id") Long taskId) {
    Task task = repository.findById(taskId).orElse(null);
    task.setCompleted();
    Project project = task.getProject();
    repository.save(task);
    updateProjectStatus(project);
    return "redirect:/tasks/" + project.getId();
  }

  @RequestMapping(value = "/incomplete/{id}")
  public String incomplete(@PathVariable("id") Long taskId) {
    Task task = repository.findById(taskId).orElse(null);
    task.setIncomplete();
    Project project = task.getProject();
    repository.save(task);
    updateProjectStatus(project);
    return "redirect:/tasks/" + project.getId();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/savetask")
  public String save(@Valid Task task, BindingResult bindingResult, Model model) {
    Project project = task.getProject();
    if (bindingResult.hasErrors()) {
      model.addAttribute("projects", projects.findAll());
      return "edittask";
    }
    repository.save(task);
    return "redirect:/tasks/" + project.getId();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping(value = "/edittask/{id}")
  public String editTask(@PathVariable("id") Long taskId, Model model) {
    model.addAttribute("task", repository.findById(taskId));
    model.addAttribute("projects", projects.findAll());
    return "edittask";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/deletetask/{id}")
  public String deleteTask(@PathVariable("id") Long taskId) {
    Project parentProject = repository.findById(taskId).orElse(null).getProject();
    repository.deleteById(taskId);
    updateProjectStatus(parentProject);
    return "redirect:/tasks";
  }

  // Mark project as completed if all its tasks are completed
  // and otherwise as incomplete
  public void updateProjectStatus(Project project) {
    if (repository.countByProjectAndCompletedIsNull(project) == 0 && repository.countByProject(project) > 0) {
      project.setCompleted();
    } else {
      project.setIncomplete();
    }
    projects.save(project);
  }
}
