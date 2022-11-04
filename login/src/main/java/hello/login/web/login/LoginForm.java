package hello.login.web.login;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 로그인 폼 객체(로그인 ID, 패스워드)
 */
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
