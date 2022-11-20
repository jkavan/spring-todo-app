package jkavan.todo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jkavan.todo.domain.User;
import jkavan.todo.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTests {

	@Autowired
	private UserRepository repository;
	
	@Test
	void testFindByUsernameShouldReturnUser() {
		User user = repository.findByUsername("admin");
		assertThat(user).isNotNull();
		assertThat(user.getRole()).isEqualTo("ADMIN");
	}
	
	@Test
	void testCreateNewUser() {
		User user = new User(
				"testUser",
				"$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
				"USER"
		);
		repository.save(user);
		assertThat(user.getId()).isNotNull();
	}

	@Test
	void testDeleteUser() {
		User user = repository.findByUsername("user");
		repository.delete(user);
		user = repository.findByUsername("user");
		assertThat(user).isNull();
		
	}
}