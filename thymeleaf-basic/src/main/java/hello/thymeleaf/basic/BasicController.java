package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 타임리프 기본 기능
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    // 1. 텍스트 (escacpe)
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-basic";
    }

    // 2. 텍스트 (Unescape)
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-unescaped";
    }

    // 3. 변수
    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 20);
        User userB = new User("userB", 25);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String ,User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("UserB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
    
    // 4. 기본 객체
    @GetMapping("/basic-objects")
    public String basisObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }
    
    @Component("helloBean") // 스프링 빈 등록
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    // 5. 유틸리티 객체와 날짜
    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    // 6. URL 링크
    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    // 7. 리터럴
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/literal";
    }

    // 8. 연산
    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    // 9. 속성 값 설정
    @GetMapping("/attribute")
    public String attribute() {
        return "basic/attribute";
    }

    // 10. 반복
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "basic/each";
    }

    // 11. 조건부 평가
    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "basic/condition";
    }

    // 12. 주석
    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data","Spring");
        return "basic/comments";
    }

    // 13. 블록
    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "basic/block";
    }

    // 14. 자바스크립트 인라인
    @GetMapping("/javascript")
    public String javascript(Model model) {
        addUsers(model);
        model.addAttribute("user", new User("userA", 10));
        return "basic/javascript";
    }

    private void addUsers(Model model) {
        List<User> list = new ArrayList<User>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));

        model.addAttribute("users", list);
    }
}
