package hello.typeconverter.converter;

import hello.typeconverter.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class PhoneNumberToStringConverter implements Converter<PhoneNumber, String> {

    @Override
    public String convert(PhoneNumber source) {
        log.info("convert source={}", source);
        return source.getFront() + source.getMiddle() + source.getBack();
    }
}
