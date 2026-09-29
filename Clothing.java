public class Clothing extends Product {
    private String size;

    public Clothing(String id, String name, double price, String size) {
        this(id, name, price, TON_KHO_MAC_DINH, size);
    }

    public Clothing(String id, String name, double price, int stock, String size) {
        super(id, name, price, stock);
        setSize(size);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty() || size.contains(";"))
            throw new IllegalArgumentException("Kích cỡ không hợp lệ");
        this.size = size.trim();
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }

    @Override
    public String getExtraInfo() {
        return "Cỡ: " + size;
    }
}
