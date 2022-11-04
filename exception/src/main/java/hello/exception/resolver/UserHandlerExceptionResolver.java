package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * UserHandlerExceptionResolver
 * UserException 오류 처리 리졸버
 * ACCEPT값이 application/json 이면 JSON으로 오류를 내려주고, 그 외 경우에는 error/500에 있는 HTML 오류 페이지 출력
 */
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    public static final String APPLICATION_JSON = "application/json";
    private final ObjectMapper objectMapper = new ObjectMapper(); // 오브젝트 -> JSON(String) 변환

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // HTTP 상태 코드 지정
                String acceptHeader = request.getHeader("accept"); // HTTP 요청 accept 헤더 정보 (JSON or HTML)

                // JSON 응답
                if ("application/json".equals(acceptHeader)) {
                    Map<String ,Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("mesage", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType(APPLICATION_JSON);
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();
                } else { // HTTP 응답
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e){
            log.info("resolver ex", e);
        }

        return null;
    }
}
