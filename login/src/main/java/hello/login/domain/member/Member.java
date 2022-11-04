package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 회원 도메인
 */
@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId; // 로그인 ID

    @NotEmpty
    private String name; // 회원 이름

    @NotEmpty
    private String password;
}
