package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * API 예외 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiExceptionController {

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        // Runtime Exception 발생 (500)
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        // IllegalArgument Exception 발생 (400)
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }
        
        // User Exception 발생
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    // ResponseStatusExceptionResolver에 의한 처리
    // @ResposeStatus 사용
    @GetMapping("/response-status-ex1")
    public String responseStatusEx1() {

        throw new BadRequestException();
    }

    // ResponseStatusException 사용
    @GetMapping("/response-status-ex2")
    public String responseStatusEx2() {

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    // DefaultHandlerExceptionResolver에 의한 처리
    @GetMapping("/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {

        return "ok";
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
