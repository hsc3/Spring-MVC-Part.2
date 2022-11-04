package hello.exception.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @RestControllerAdvice
 * Rest Controller에 대한 공통 예외 처리
 */
@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    // IllegalArgument Exception 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 응답 상태 코드: 200 -> 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {

        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    // User Exception 처리
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {

        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 그 외 Exception 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {

        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
