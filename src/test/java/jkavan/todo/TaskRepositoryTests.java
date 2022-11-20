package jkavan.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jkavan.todo.domain.ProjectRepository;
import jkavan.todo.domain.Task;
import jkavan.todo.domain.TaskRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskRepositoryTests {

  @Autowired
  private TaskRepository repository;

  @Autowired
  private ProjectRepository projects;

  @Test
  void testFindTaskShouldReturnTask() {
    Task task = repository.findByTitle("Finish SWD4TA020-course").get(0);
    assertThat(task).isNotNull();
  }
  
  @Test
  void testCreateNewTask() {
    Task task = new Task("Automate everything", projects.findByName("Home automation").get(0));
    repository.save(task);
    assertThat(task.getId()).isNotNull();
  }

  @Test
  void testDeleteTask() {
    Task task = repository.findByTitle("Finish SWD4TA020-course").get(0);
    repository.delete(task);
    List<Task> tasks = repository.findByTitle("Finish SWD4TA020-course");
    assertThat(tasks).hasSize(0);
  }

}
