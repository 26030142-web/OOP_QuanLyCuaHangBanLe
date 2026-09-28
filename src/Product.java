import java.math.BigDecimal;
import java.util.Objects;

public abstract class Product {
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    private final String id;
    private String name;
    private BigDecimal price;
    private int stock;
    private boolean active;

    public Product(String id, String name, BigDecimal price) {
        this(id, name, price, 0);
    }

    public Product(String id, String name, BigDecimal price, int stock) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được rỗng");
        }

        this.id = id.trim();
        setName(name);
        setPrice(price);
        setStock(stock);
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
        }
        this.name = name.trim();
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Tồn kho không được âm");
        }
        this.stock = stock;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract String category();

    public abstract String specialInfo();

    @Override
    public String toString() {
        return id + " | " + name + " | " + price
                + " | tồn kho: " + stock + " | loại: " + category();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
