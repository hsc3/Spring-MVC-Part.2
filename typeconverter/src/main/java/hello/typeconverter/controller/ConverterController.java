package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import hello.typeconverter.type.PhoneNumber;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConverterController {

    // 뷰 - 컨버터 적용
    @GetMapping("/converter-view")
    public String converterView(Model model) {

        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        model.addAttribute("phoneNumber", new PhoneNumber("010", "1234", "5678"));
        return "converter-view";
    }

    // 폼 - 컨버터 적용
    @GetMapping("/ipPort/edit")
    public String ipPortForm(Model model) {

        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        IpPortForm ipPortForm = new IpPortForm(ipPort);

        model.addAttribute("form", ipPortForm);
        return "ipPort-form";
    }

    @PostMapping("/ipPort/edit")
    public String converterEdit(@ModelAttribute("form") IpPortForm form, Model model) {

        IpPort ipPort = form.getIpPort();
        model.addAttribute("ipPort", ipPort);
        return "converter-view";
    }

    @GetMapping("/phoneNumber/edit")
    public String phoneNumberForm(Model model) {

        PhoneNumber phoneNumber = new PhoneNumber("010", "1234", "5678");
        PhoneNumberForm phoneNumberForm = new PhoneNumberForm(phoneNumber);

        model.addAttribute("form", phoneNumberForm);
        return "phoneNumber-form";
    }

    @PostMapping("/phoneNumber/edit")
    public String converterEdit(@ModelAttribute("form") PhoneNumberForm form, Model model) {
        PhoneNumber phoneNumber = form.getPhoneNumber();
        model.addAttribute("phoneNumber", phoneNumber);
        return "converter-view";
    }

    @Data
    static class IpPortForm {
        private IpPort ipPort;

        public IpPortForm(IpPort ipPort) {
            this.ipPort = ipPort;
        }
    }

    @Data
    static class PhoneNumberForm {
        private PhoneNumber phoneNumber;

        public PhoneNumberForm(PhoneNumber phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
