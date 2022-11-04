package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ResponseStatus
 * HTTP 상태 코드 지정 (BadRequestException 발생 시, HTTP 상태 코드 400 지정)
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {
}
