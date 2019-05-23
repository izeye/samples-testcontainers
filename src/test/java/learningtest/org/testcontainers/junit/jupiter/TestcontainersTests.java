package learningtest.org.testcontainers.junit.jupiter;

import learningtest.org.testcontainers.SkippableContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Testcontainers}.
 *
 * @author Johnny Lim
 */
@Testcontainers
class TestcontainersTests {

	@Container
	private static final SkippableContainer<MySQLContainer> MY_SQL_CONTAINER = new SkippableContainer<>(MySQLContainer::new);

	@Container
	private final SkippableContainer<PostgreSQLContainer> postgreSQLContainer = new SkippableContainer<>(PostgreSQLContainer::new);

	@Test
	void test() {
		assertThat(MY_SQL_CONTAINER.getContainer().isRunning()).isTrue();
		assertThat(this.postgreSQLContainer.getContainer().isRunning()).isTrue();
	}

}
