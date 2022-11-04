package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>(); // [세션 ID, 실제값]

    // 세션 생성
    public void createSession(Object value, HttpServletResponse response) {

        // value(회원 정보)를 랜덤 세션 ID 값으로 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value); 

        // 세션 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }
    
    // 세션 조회
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) { // 세션 쿠키 X
            return null;
        }
        return sessionStore.get(sessionCookie.getValue()); // 세션 ID에 해당하는 세션 반환
    }

    // 세션 만료
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        // HTTP request 객체에 저장된 쿠키가 없는 경우
        if (request.getCookies() == null) {
            return null;
        }

        // HTTP request 객체에 쿠기들이 존재하는 경우 -> 세션 쿠키를 찾아서 반환
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
