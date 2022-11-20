package jkavan.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jkavan.todo.domain.Project;
import jkavan.todo.domain.ProjectRepository;
import jkavan.todo.domain.Task;
import jkavan.todo.domain.TaskRepository;
import jkavan.todo.domain.User;
import jkavan.todo.domain.UserRepository;

@SpringBootApplication
public class TodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(ProjectRepository projects, TaskRepository tasks, UserRepository users) {
    return (args) -> {
      projects.save(new Project("Home automation"));
      projects.save(new Project("Education"));
      tasks.save(new Task("Finish SWD4TA020-course", projects.findByName("Education").get(0)));
      tasks.save(new Task("Graduate from school", projects.findByName("Education").get(0)));
      tasks.save(new Task("Automate outdoor lights", projects.findByName("Home automation").get(0)));
      tasks.save(new Task("Automate bathroom lights", projects.findByName("Home automation").get(0)));

      for (Task task : tasks.findAll()) {
        System.out.println(task);
      }

      User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
      User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
      users.save(user1);
      users.save(user2);
    };
  }

}
