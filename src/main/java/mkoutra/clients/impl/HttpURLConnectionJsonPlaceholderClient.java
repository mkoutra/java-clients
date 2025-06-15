package mkoutra.clients.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mkoutra.clients.JsonPlaceholderClient;
import mkoutra.entity.PostEntity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * A client using Java's built-in HttpURLConnection.
 */
public class HttpURLConnectionJsonPlaceholderClient implements JsonPlaceholderClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final ObjectMapper objectMapper;

    public HttpURLConnectionJsonPlaceholderClient() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<PostEntity> getPosts() throws Exception {
        URL url = new URL(BASE_URL + "/posts");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        InputStream inputStream = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            if (status < 200 || status >= 300) {
                throw new IOException("Failed : HTTP error code : " + status + " - " + responseBuilder.toString());
            }

            String json = responseBuilder.toString();
            return objectMapper.readValue(json, new TypeReference<List<PostEntity>>() {});
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public PostEntity createPost(PostEntity post) throws Exception {
        URL url = new URL(BASE_URL + "/posts");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        String jsonBody = objectMapper.writeValueAsString(post);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = conn.getResponseCode();
        InputStream inputStream = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line.trim());
            }

            if (status < 200 || status >= 300) {
                throw new IOException("Failed : HTTP error code : " + status + " - " + responseBuilder.toString());
            }

            String json = responseBuilder.toString();
            return objectMapper.readValue(json, PostEntity.class);
        } finally {
            conn.disconnect();
        }
    }
}
