package nice.jongwoo.member;

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
@Table(name = "member")
public class Member {

    private static final String MEMBER_PREFIX = "mem_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String userToken;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String role;


    @Builder
    public Member(String email, String userName, String password) {
        this.userToken = TokenGenerator.randomCharacterWithPrefix(MEMBER_PREFIX);
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.role = Role.USER.getValue();
    }
}
