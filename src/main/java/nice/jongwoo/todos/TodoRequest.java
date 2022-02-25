package nice.jongwoo.todos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequest {

    private String title;
    private String todoDate;

    public static Todo toEntity(final TodoRequest request) {
        return Todo.builder()
            .title(request.getTitle())
            .todoDate(request.getTodoDate())
            .build();
    }
}
