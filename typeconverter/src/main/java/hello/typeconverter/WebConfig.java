package hello.typeconverter;

import hello.typeconverter.converter.*;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 컨버터, 포맷터 등록
    @Override
    public void addFormatters(FormatterRegistry registry) {

        //컨버터 등록
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToPhoneNumberConverter());
        registry.addConverter(new PhoneNumberToStringConverter());
        //포맷터 등록
        registry.addFormatter(new MyNumberFormatter());
    }
}
