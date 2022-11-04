package hello.exception.exception.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 예외 발생시 API 응답으로 사용하는 객체
 */
@Data
@AllArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
}
