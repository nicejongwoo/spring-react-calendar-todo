package nice.jongwoo.todos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nice.jongwoo.common.TokenGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "todo")
public class Todo {

    private static final String TODO_PREFIX = "todo_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String todoToken;

    private String title;
    private String todoDate;
    private boolean done;

    private String createdBy;
    private LocalDateTime createdAt;
    private String modifiedBy;
    private LocalDateTime modifiedAt;

    @Builder
    public Todo(String title, String todoDate) {
        this.todoToken = TokenGenerator.randomCharacterWithPrefix(TODO_PREFIX);
        this.title = title;
        this.todoDate = todoDate;
        this.done = false;
    }
}

