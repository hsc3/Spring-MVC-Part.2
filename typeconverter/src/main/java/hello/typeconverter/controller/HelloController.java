package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import hello.typeconverter.type.PhoneNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    // === 스프링이 등록한 컨버터 ===
    // 수동 형변환
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); // 문자 타입 조회
        Integer intValue = Integer.valueOf(data); // 숫자 타입으로 변환
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    // @RequestParam의 자동 형변환
    @GetMapping("/type-convert")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }
    
    // === 직접 등록한 컨버터 사용 ===
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort ip = " + ipPort.getIp());
        System.out.println("ipPort port = " + ipPort.getPort());
        return "ok";
    }

    @GetMapping("phone-number")
    public String phoneNumber(@RequestParam PhoneNumber phoneNumber) {
        System.out.println("phoneNumber = " + phoneNumber.getPhoneNumber());
        return "ok";
    }
}
