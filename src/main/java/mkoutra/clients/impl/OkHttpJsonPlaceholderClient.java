package mkoutra.clients.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mkoutra.clients.JsonPlaceholderClient;
import mkoutra.entity.PostEntity;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * OkHttpClient
 */
public class OkHttpJsonPlaceholderClient implements JsonPlaceholderClient {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public OkHttpJsonPlaceholderClient() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<PostEntity> getPosts() throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL + "/posts")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);

            String json = response.body().string();
            return objectMapper.readValue(json, new TypeReference<List<PostEntity>>() {});
        }
    }

    @Override
    public PostEntity createPost(PostEntity post) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(post);

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/posts")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);

            String responseJson = response.body().string();
            return objectMapper.readValue(responseJson, PostEntity.class);
        }
    }
}
