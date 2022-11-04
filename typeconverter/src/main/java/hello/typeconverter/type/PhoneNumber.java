package hello.typeconverter.type;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PhoneNumber {

    private String front; // 010
    private String middle; // 1234
    private String back; // 5678

    public PhoneNumber(String front, String middle, String back) {
        this.front = front;
        this.middle = middle;
        this.back = back;
    }

    public String getPhoneNumber() {
        return front + "-" + middle + "-" + back; // 010-1234-5678
    }

}
