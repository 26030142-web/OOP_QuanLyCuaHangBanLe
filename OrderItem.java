public class OrderItem {
    private Product product;
    private String productName;
    private double unitPrice;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null)
            throw new IllegalArgumentException("Chưa chọn sản phẩm");
        this.product = product;
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
        setQuantity(quantity);
    }

    // Constructor này được dùng khi đọc dòng hàng từ file.
    public OrderItem(String productName, double unitPrice, int quantity) {
        this(null, productName, unitPrice, quantity);
    }

    private OrderItem(Product product, String productName, double unitPrice, int quantity) {
        if (productName == null || productName.trim().isEmpty())
            throw new IllegalArgumentException("Tên sản phẩm không hợp lệ");
        if (unitPrice <= 0)
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        this.product = product;
        this.productName = productName;
        this.unitPrice = unitPrice;
        setQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        this.quantity = quantity;
    }

    public double getAmount() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return productName + " | " + quantity + " x " + unitPrice + " = " + getAmount();
    }
}
