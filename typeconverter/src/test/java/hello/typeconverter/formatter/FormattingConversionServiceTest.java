package hello.typeconverter.formatter;

import hello.typeconverter.converter.PhoneNumberToStringConverter;
import hello.typeconverter.converter.StringToPhoneNumberConverter;
import hello.typeconverter.type.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {

        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        
        //컨버터 등록
        conversionService.addConverter(new PhoneNumberToStringConverter());
        //포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        //컨버터 사용
        assertThat(conversionService.convert(new PhoneNumber("010", "1234", "5678"), String.class))
                .isEqualTo("01012345678");
        
        //포맷터 사용
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
    }
}
