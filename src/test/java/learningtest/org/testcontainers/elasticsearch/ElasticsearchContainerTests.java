package learningtest.org.testcontainers.elasticsearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ElasticsearchContainer}.
 *
 * @author Johnny Lim
 */
public class ElasticsearchContainerTests {

	@Test
	public void test() throws IOException {
		ElasticsearchContainer container = new ElasticsearchContainer(
				"docker.elastic.co/elasticsearch/elasticsearch:6.4.1");
		container.start();

		RestClient restClient = RestClient.builder(HttpHost.create(container.getHttpHostAddress())).build();
		Response response = restClient.performRequest(new Request("GET", "/"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		response.getEntity().writeTo(baos);
		String responseBody = baos.toString();
		System.out.println(responseBody);
		assertThat(responseBody).contains("\"number\" : \"6.4.1\",");

		String clusterName = "docker-cluster";
		TransportAddress transportAddress = new TransportAddress(container.getTcpHost());
		Settings settings = Settings.builder().put("cluster.name", clusterName).build();
		TransportClient transportClient = new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);
		ClusterHealthResponse clusterHealthResponse = transportClient.admin().cluster().prepareHealth().get();
		System.out.println(clusterHealthResponse);
		assertThat(clusterHealthResponse.getClusterName()).isEqualTo(clusterName);

		container.stop();
	}

}
