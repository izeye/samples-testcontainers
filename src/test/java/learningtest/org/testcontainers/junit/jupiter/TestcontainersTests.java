package learningtest.org.testcontainers.junit.jupiter;

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
	private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer();

	@Container
	private final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

	@Test
	void test() {
		assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
		assertThat(this.postgreSQLContainer.isRunning()).isTrue();
	}

}
