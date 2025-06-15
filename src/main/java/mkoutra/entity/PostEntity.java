package mkoutra.entity;

import lombok.*;

/**
 * Entity necessary to communicate with
 * JSONPlaceholder API: https://jsonplaceholder.typicode.com/guide/
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    private Integer id;
    private Integer userId;
    private String title;
    private String body;

    public PostEntity(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
