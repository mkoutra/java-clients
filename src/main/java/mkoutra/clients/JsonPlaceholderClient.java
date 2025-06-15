package mkoutra.clients;

import mkoutra.entity.PostEntity;

import java.util.List;

/**
 * Interface for JsonPlaceholder public API: https://jsonplaceholder.typicode.com/guide/
 */
public interface JsonPlaceholderClient {
    List<PostEntity> getPosts() throws Exception;
    PostEntity createPost(PostEntity post) throws Exception;
}
