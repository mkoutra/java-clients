package mkoutra;

import mkoutra.clients.JsonPlaceholderClient;
import mkoutra.clients.impl.ApacheJsonPlaceholderClient;
import mkoutra.clients.impl.HttpURLConnectionJsonPlaceholderClient;
import mkoutra.clients.impl.OkHttpJsonPlaceholderClient;
import mkoutra.entity.PostEntity;

import java.util.List;

/**
 * Testing Clients: Apache httpclient, okhttp and httpurlconnection.
 * @author Michalis Koutrakis
 */
public class HttpClientApp {

    // Choose your client: APACHE, OKHTTP or HTTP_URL
    private static final HttpClientType CLIENT_TYPE = HttpClientType.HTTP_URL;

    public static void main(String[] args) {

        JsonPlaceholderClient client = createClient(CLIENT_TYPE);

        try {
            System.out.println("Client used: " + CLIENT_TYPE.name());

            // GET all posts
            System.out.println("Fetching posts...");
            List<PostEntity> posts = client.getPosts();

            for (PostEntity post : posts) {
                System.out.println("[" + post.getId() + "] " + post.getTitle());
            }

            // POST a new post
            System.out.println("\nCreating a new post...");

            PostEntity newPost = new PostEntity(99, "Learning about Java HTTP Clients", "This is the post body.");
            PostEntity createdPost = client.createPost(newPost);

            System.out.printf("Created post with ID: %d and Title: %s\n", createdPost.getId(), createdPost.getTitle());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JsonPlaceholderClient createClient(HttpClientType clientType) {
        switch (clientType) {
            case OKHTTP:
                return new OkHttpJsonPlaceholderClient();
            case HTTP_URL:
                return new HttpURLConnectionJsonPlaceholderClient();
            case APACHE:
            default:
                return new ApacheJsonPlaceholderClient();
        }
    }
}
