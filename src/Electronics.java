import java.math.BigDecimal;

public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics(String id, String name, BigDecimal price,
                       int warrantyMonths) {
        this(id, name, price, 0, warrantyMonths);
    }

    public Electronics(String id, String name, BigDecimal price,
                       int stock, int warrantyMonths) {
        super(id, name, price, stock);
        setWarrantyMonths(warrantyMonths);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException(
                    "Số tháng bảo hành không được âm");
        }
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String category() {
        return "Electronics";
    }

    @Override
    public String specialInfo() {
        return String.valueOf(warrantyMonths);
    }

    @Override
    public String toString() {
        return super.toString()
                + " | bảo hành: " + warrantyMonths + " tháng";
    }
}
