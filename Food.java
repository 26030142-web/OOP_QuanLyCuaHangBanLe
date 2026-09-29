import java.time.LocalDate;

public class Food extends Product {
    private String expiryDate;

    public Food(String id, String name, double price, String expiryDate) {
        this(id, name, price, TON_KHO_MAC_DINH, expiryDate);
    }

    public Food(String id, String name, double price, int stock, String expiryDate) {
        super(id, name, price, stock);
        setExpiryDate(expiryDate);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        try {
            LocalDate.parse(expiryDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Hạn sử dụng phải đúng dạng yyyy-MM-dd");
        }
        this.expiryDate = expiryDate;
    }

    @Override
    public String getCategory() {
        return "Food";
    }

    @Override
    public String getExtraInfo() {
        return "HSD: " + expiryDate;
    }
}
