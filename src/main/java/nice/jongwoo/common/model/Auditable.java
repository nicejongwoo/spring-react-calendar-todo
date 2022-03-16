package nice.jongwoo.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

    @Column(name = "created_By")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
