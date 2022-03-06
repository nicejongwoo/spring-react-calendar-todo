package nice.jongwoo.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

    private String email;
    private String userName;
    private String password;

    public static Member toEntity(final MemberRequest request) {
        return Member.builder()
            .email(request.getEmail())
            .userName(request.getUserName())
            .password(request.getPassword())
            .build();

    }
}
