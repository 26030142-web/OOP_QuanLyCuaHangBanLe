import java.math.BigDecimal;

public class Clothing extends Product {
    private String size;

    public Clothing(String id, String name, BigDecimal price, String size) {
        this(id, name, price, 0, size);
    }

    public Clothing(String id, String name, BigDecimal price,
                    int stock, String size) {
        super(id, name, price, stock);
        setSize(size);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Kích cỡ quần áo không được rỗng");
        }
        this.size = size.trim();
    }

    @Override
    public String category() {
        return "Clothing";
    }

    @Override
    public String specialInfo() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() + " | size: " + size;
    }
}
