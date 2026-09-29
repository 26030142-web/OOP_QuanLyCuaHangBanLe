// Lop dai dien cho mot dong san pham ben trong don hang.
public class OrderItem {
    private Product sanPham;
    private String tenSanPham;
    private double donGia;
    private int soLuong;

    // Ham tao luu tham chieu san pham va chup ten/gia tai thoi diem ban.
    public OrderItem(Product sanPham, int soLuong) {
        if (sanPham == null) throw new IllegalArgumentException("Hay chon san pham truoc.");
        this.sanPham = sanPham;
        this.tenSanPham = sanPham.layTen();
        this.donGia = sanPham.layGia();
        ganSoLuong(soLuong);
    }

    // Ham tao ngan noi voi ham tao dung khi doc du lieu tu file.
    public OrderItem(String tenSanPham, double donGia, int soLuong) {
        this(null, tenSanPham, donGia, soLuong);
    }

    // Ham tao noi bo khoi phuc dong hang tu du lieu da luu.
    private OrderItem(Product sanPham, String tenSanPham, double donGia, int soLuong) {
        if (tenSanPham == null || tenSanPham.trim().isEmpty()) throw new IllegalArgumentException("Ten san pham khong duoc de trong.");
        if (donGia <= 0 || Double.isNaN(donGia) || Double.isInfinite(donGia)) {
            throw new IllegalArgumentException("Don gia phai la so hop le lon hon 0.");
        }
        this.sanPham = sanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        ganSoLuong(soLuong);
    }

    // Tra ve san pham lien ket voi dong hang.
    public Product laySanPham() { return sanPham; }

    // Tra ve ten san pham da chup tai luc lap don.
    public String layTenSanPham() { return tenSanPham; }

    // Tra ve don gia da chup tai luc lap don.
    public double layDonGia() { return donGia; }

    // Tra ve so luong san pham trong dong hang.
    public int laySoLuong() { return soLuong; }

    // Chi nhan so luong lon hon 0.
    public void ganSoLuong(int soLuong) {
        if (soLuong <= 0) throw new IllegalArgumentException("So luong phai lon hon 0.");
        this.soLuong = soLuong;
    }

    // Tinh tien cua dong hang.
    public double tinhThanhTien() { return donGia * soLuong; }

    // Ghi de de hien thi dong hang.
    @Override
    public String toString() {
        return tenSanPham + " | " + soLuong + " x " + donGia + " = " + tinhThanhTien();
    }
}
