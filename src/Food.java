import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Food extends Product {
    private String expiryDate;

    public Food(String id, String name, BigDecimal price, String expiryDate) {
        this(id, name, price, 0, expiryDate);
    }

    public Food(String id, String name, BigDecimal price,
                int stock, String expiryDate) {
        super(id, name, price, stock);
        setExpiryDate(expiryDate);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("Hạn sử dụng không được rỗng");
        }

        try {
            LocalDate.parse(expiryDate); // Định dạng yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Hạn sử dụng phải có dạng yyyy-MM-dd", e);
        }

        this.expiryDate = expiryDate;
    }

    @Override
    public String category() {
        return "Food";
    }

    @Override
    public String specialInfo() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return super.toString() + " | HSD: " + expiryDate;
    }
}
