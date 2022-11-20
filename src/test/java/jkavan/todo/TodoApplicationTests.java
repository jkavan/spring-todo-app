package jkavan.todo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jkavan.todo.web.ProjectController;
import jkavan.todo.web.TaskController;

@SpringBootTest
class TodoApplicationTests {
  
  @Autowired
  private TaskController taskController;
  
  @Autowired
  private ProjectController projectController;
  
  @Test
  void contextLoads() {
    assertThat(taskController).isNotNull();
    assertThat(projectController).isNotNull();
  }

}
