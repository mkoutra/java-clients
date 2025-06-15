package mkoutra.clients.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mkoutra.clients.JsonPlaceholderClient;
import mkoutra.entity.PostEntity;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.util.List;

/**
 * Apache http client
 */
public class ApacheJsonPlaceholderClient implements JsonPlaceholderClient {
    private final static String API_URL = "https://jsonplaceholder.typicode.com/posts";

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApacheJsonPlaceholderClient() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<PostEntity> getPosts() throws Exception {
        HttpGet request = new HttpGet(API_URL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String json = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(json, new TypeReference<List<PostEntity>>() {});
        }
    }

    @Override
    public PostEntity createPost(PostEntity newPost) throws Exception {
        HttpPost request = new HttpPost(API_URL);
        request.addHeader("Content-Type", "application/json");

        String json = objectMapper.writeValueAsString(newPost);
        request.setEntity(new StringEntity(json));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(responseBody, PostEntity.class);
        }
    }
}
