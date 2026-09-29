public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics(String id, String name, double price, int warrantyMonths) {
        this(id, name, price, TON_KHO_MAC_DINH, warrantyMonths);
    }

    public Electronics(String id, String name, double price, int stock, int warrantyMonths) {
        super(id, name, price, stock);
        setWarrantyMonths(warrantyMonths);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0)
            throw new IllegalArgumentException("Tháng bảo hành không được âm");
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    public String getExtraInfo() {
        return "Bảo hành: " + warrantyMonths + " tháng";
    }
}
