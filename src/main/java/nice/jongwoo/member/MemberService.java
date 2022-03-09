package nice.jongwoo.member;

public interface MemberService {

    Member registerMember(Member member);

    Member getByCredentials(MemberRequest request);
}
