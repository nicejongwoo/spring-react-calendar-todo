package nice.jongwoo.todos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nice.jongwoo.common.TokenGenerator;
import nice.jongwoo.common.model.Auditable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "todo")
public class Todo extends Auditable<String> implements Serializable {

    private static final String TODO_PREFIX = "todo_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String todoToken;

    private String title;
    private String todoDate;
    private boolean done;


    @Builder
    public Todo(String title, String todoDate) {
        this.todoToken = TokenGenerator.randomCharacterWithPrefix(TODO_PREFIX);
        this.title = title;
        this.todoDate = todoDate;
        this.done = false;
    }

    public void changeOnTrue() {
        this.done = true;
    }

    public void changeOnFalse() {
        this.done = false;
    }

    public void edit(String title, String todoDate) {
        this.title = title;
        this.todoDate = todoDate;
    }
}

