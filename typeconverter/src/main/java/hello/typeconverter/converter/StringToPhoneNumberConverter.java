package hello.typeconverter.converter;

import hello.typeconverter.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * 전화번호 컨버터 ("01012345678" --> "010-1234-5678")
 */
@Slf4j
public class StringToPhoneNumberConverter implements Converter<String, PhoneNumber> {

    @Override
    public PhoneNumber convert(String source) {
        log.info("convert source={}", source);
        String front = source.substring(0, 3);
        String middle = source.substring(3, 7);
        String back = source.substring(7, 11);
        return new PhoneNumber(front, middle, back);
    }
}
