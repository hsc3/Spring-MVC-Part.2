package hello.itemservice.domain.item;

/**
 * 상품 종류
 */
public enum ItemType {

    FOOD("음식"), BOOK("도서"), ETC("기타");

    private final String description; // 상품 설명

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
