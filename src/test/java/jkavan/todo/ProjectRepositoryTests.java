package jkavan.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jkavan.todo.domain.ProjectRepository;
import jkavan.todo.domain.Project;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProjectRepositoryTests {

  @Autowired
  private ProjectRepository repository;

  @Test
  void testFindProjectShouldReturnProject() {
    Project project = repository.findByName("Home automation").get(0);
    assertThat(project).isNotNull();
  }
  
  @Test
  void testCreateNewProject() {
    Project project = new Project("A new project");
    repository.save(project);
    assertThat(project.getId()).isNotNull();
  }

  @Test
  void testDeleteProject() {
    Project project = repository.findByName("Finish SWD4TA020-course").get(0);
    repository.delete(project);
    List<Project> projects = repository.findByName("Finish SWD4TA020-course");
    assertThat(projects).hasSize(0);
  }

}
