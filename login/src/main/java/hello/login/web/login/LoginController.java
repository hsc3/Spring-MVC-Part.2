package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 로그인 화면 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    // === 로그인 화면 이동 ===
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form) {
        return "login/loginForm"; // 로그인 화면 뷰 템플릿으로 이동
    }

    // === 로그인 처리 ===
    // v1 : 쿠키만 적용
//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        // 회원 ID, 패스워드 입력 값 검증
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 입력받은 회원 ID, 패스워드의 검증을 완료한 후
        // memberRepository에 해당 회원의 유무 검증 (글로벌 오류 검증)
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 검증 성공 로직
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie); // 쿠키 생성 -> 로그인 상태 유지
        return "redirect:/"; // 홈화면 리다이렉트
    }

    // v2 : 세션 + 쿠키 적용
//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        // 회원 ID, 패스워드 입력 값 검증
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 입력받은 회원 ID, 패스워드의 검증을 완료한 후
        // memberRepository에 해당 회원의 유무 검증 (글로벌 오류 검증)
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 검증 성공 로직 (세션 쿠키 사용)
        sessionManager.createSession(loginMember, response);
        return "redirect:/";
    }

    // v3 : HTTP 세션 사용
//    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        // 회원 ID, 패스워드 입력 값 검증
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 입력받은 회원 ID, 패스워드의 검증을 완료한 후
        // memberRepository에 해당 회원의 유무 검증 (글로벌 오류 검증)
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 검증 성공 로직 (HTTP 세션에 로그인 정보 저장)
        // 세션이 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    // v4 : 인증 필터 적용 (미인증 사용자가 로그인 시, 기존 페이지로 리다이렉트)
    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                          HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {

        // 회원 ID, 패스워드 입력 값 검증
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        // 입력받은 회원 ID, 패스워드의 검증을 완료한 후
        // memberRepository에 해당 회원의 유무 검증 (글로벌 오류 검증)
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 검증 성공 로직 (HTTP 세션에 로그인 정보 저장)
        // 세션이 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:" + redirectURL; // 기존 화면으로 리다이렉트 (기본값은 홈화면)
    }
    
    // === 로그아웃 처리 ===
    // v1 : 쿠키 적용
//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId"); // 세션 만료 쿠키 생성 (로그인 회원 ID 삭제)
        return "redirect:/";
    }

    // v2 : 세션 + 쿠키 적용
//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 신규 생성X
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // 로그아웃 시, 쿠키를 즉시 삭제 (로그인 회원 ID 삭제)
        response.addCookie(cookie);
    }
}
