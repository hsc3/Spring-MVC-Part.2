package hello.itemservice.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 상품 저장, 수정 기능의 검증 분리 적용
 * ItemSaveForm : addForm에서 사용하는 item 객체
 */
@Data
public class ItemSaveForm {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999) // 등록 시에만 검증 적용
    private Integer quantity;
}
