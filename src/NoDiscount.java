import java.math.BigDecimal;

interface DiscountStrategy {
    BigDecimal apply(BigDecimal amount);

    default String description() {
        return "Không giảm giá";
    }
}

public class NoDiscount implements DiscountStrategy {

    @Override
    public BigDecimal apply(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Số tiền không được âm hoặc rỗng");
        }
        return amount;
    }
}
