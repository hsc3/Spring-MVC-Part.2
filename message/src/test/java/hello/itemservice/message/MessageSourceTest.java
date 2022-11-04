package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import java.util.Locale;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() { // default 메시지
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕하세요");
    }

    @Test
    void notFoundMessageCode() { // 메시지 코드가 없는 경우
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeToDefaultMessage() { // 메시지 코드가 없는 경우, 기본 메시지 지정
        String result = messageSource.getMessage("no_code", null, "디폴트 메시지", null);
        System.out.println("message : " + result);
        assertThat(result).isEqualTo("디폴트 메시지");
    }

    @Test
    void argumentMessage() { // 매개변수를 사용하는 메시지
        String result = messageSource.getMessage("hello.name", new Object[]{"Choi"}, null);
        assertThat(result).isEqualTo("안녕하세요 Choi");
    }

    @Test
    void defaultLanguage() { // 국제화 파일 선택 - default, 한국어
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕하세요");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕하세요");
    }
    
    @Test
    void enLanguage() { // 국제화 파일 선택 - 미국어
        assertThat(messageSource.getMessage("hello.name", new Object[]{"Choi"}, Locale.ENGLISH)).isEqualTo("hello Choi");
    }
}
