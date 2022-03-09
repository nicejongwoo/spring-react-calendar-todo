package nice.jongwoo.member;

import lombok.Getter;

@Getter
public enum Role {

    USER("ROLE_USER", "일반사용자권한"),
    ADMIN("ROLE_ADMIN", "관리자권한")
    ;

    private String value;
    private String desc;

    Role(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
