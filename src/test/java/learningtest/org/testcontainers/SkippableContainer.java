package learningtest.org.testcontainers;

import java.util.function.Supplier;

import org.junit.jupiter.api.Assumptions;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startable;

/**
 * A {@link GenericContainer} decorator that skips test execution when Docker is not
 * available.
 *
 * @param <T> type of the underlying container
 * @author Andy Wilkinson
 * @author Madhura Bhave
 */
public class SkippableContainer<T extends GenericContainer> implements Startable {

	private final Supplier<T> containerFactory;

	private T container;

	public SkippableContainer(Supplier<T> containerFactory) {
		this.containerFactory = containerFactory;
	}

	public T getContainer() {
		if (this.container == null) {
			throw new IllegalStateException(
					"Container cannot be accessed prior to test invocation");
		}
		return this.container;
	}

	@Override
	public void start() {
		Assumptions.assumeTrue(isDockerRunning(),
				"Could not find valid docker environment.");
		this.container = this.containerFactory.get();
		this.container.start();
	}

	private boolean isDockerRunning() {
		try {
			DockerClientFactory.instance().client();
			return true;
		}
		catch (Throwable ex) {
			return false;
		}
	}

	@Override
	public void stop() {
		if (this.container != null) {
			this.container.stop();
		}
	}

}
