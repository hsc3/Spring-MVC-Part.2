package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * 객체 -> 문자 or 문자 -> 객체 변환에 특화, 현지화 정보를 사용할 수 있는 포맷터
 */
@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    // 문자 -> 객체
    @Override
    public Number parse(String text, Locale locale) throws ParseException {

        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale); // 현지 포맷
        return format.parse(text); // 1,000 -> 1000
    }

    // 객체 -> 문자
    @Override
    public String print(Number object, Locale locale) {

        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object); // 1000 -> 1,000
    }
}
