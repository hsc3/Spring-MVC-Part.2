package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 적용 대상
@Retention(RetentionPolicy.RUNTIME) // 지속 시간
public @interface Login {
}
