package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 홈 화면 컨트롤러: 로그인 유무에 따라 분리된 홈 화면 이동
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    // 쿠키 적용
//    @GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model) {

        // 로그인 정보 X
        if (memberId == null) {
            return "home"; // 홈화면 뷰 템플릿 이동
        }

        // 로그인 정보 O
        Member loginMember = memberRepository.findById(memberId); // 해당 ID의 회원 탐색
        if (loginMember == null) { // 저장소에 해당 회원이 존재 X
            return "home";
        }

        model.addAttribute("member", loginMember); // 회원의 정보를 모델 저장 (홈화면 출력)
        return "loginHome"; // 로그인 상태 홈화면 뷰 템플릿 이동
        
    }

    // 세션 + 쿠키 적용
//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Object member = sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome"; // 로그인 상태의 홈화면 뷰 템플릿 이동
    }

    // HTTP 세션 적용
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        // 세션 정보 X -> home 이동
        HttpSession session = request.getSession(false); // 신규 세션 생성X
        if (session == null) {
            return "home";
        }

        // 세션에 저장된 회원 정보 조회
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    // @SessionAttribute 사용
//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    // ArgumentResolver 활용
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member member, Model model) {

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}