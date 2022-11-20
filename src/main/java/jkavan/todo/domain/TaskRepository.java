package jkavan.todo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
  List<Task> findByTitle(String name);

  List<Task> findByProject(Project project);

  long countByProject(Project project);

  long countByProjectAndCompletedIsNull(Project project);
}
