import java.util.Objects;

public abstract class Product {
    public static final int TON_KHO_MAC_DINH = 0;
    private String id;
    private String name;
    private double price;
    private int stock;

    public Product(String id, String name, double price) {
        this(id, name, price, TON_KHO_MAC_DINH);
    }

    public Product(String id, String name, double price, int stock) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty() || id.contains(";"))
            throw new IllegalArgumentException("Mã sản phẩm không hợp lệ");
        this.id = id.trim();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty() || name.contains(";"))
            throw new IllegalArgumentException("Tên sản phẩm không hợp lệ");
        this.name = name.trim();
    }

    public void setPrice(double price) {
        if (price <= 0 || Double.isNaN(price) || Double.isInfinite(price))
            throw new IllegalArgumentException("Giá phải là số lớn hơn 0");
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("Tồn kho không được âm");
        this.stock = stock;
    }

    public abstract String getCategory();

    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return id + " | " + name + " | " + price + " | tồn: " + stock + " | " + getCategory() + " | " + getExtraInfo();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Product))
            return false;
        Product other = (Product) object;
        return id.equalsIgnoreCase(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }
}
