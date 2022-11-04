package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"}; // 로그인없이 접근할 수 있는 URL

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        try {
            if (isLoginCheckPath(requestURI)) { // 로그인 여부를 판단해야 하는 경우
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpServletRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) { // 로그인 정보 X
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI); // 로그인 화면으로 리다이렉트
                    return;
                }
            }

            chain.doFilter(request, response); // 다음 컨트롤러 로직 수행
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    // 로그인 여부를 체크해야 하는 URL인지 판단 (true: 로그인 필요)
    private Boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}