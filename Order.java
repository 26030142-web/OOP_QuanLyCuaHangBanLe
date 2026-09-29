import java.util.ArrayList;
import java.util.Objects;

// Lop don hang chua mot khach hang va cac dong san pham.
public class Order {
    private String ma;
    private Customer khachHang;
    private ArrayList<OrderItem> danhSachMatHang;

    // Ham tao ngan tao danh sach dong hang rong.
    public Order(String ma, Customer khachHang) {
        this(ma, khachHang, new ArrayList<OrderItem>());
    }

    // Ham tao day du dung khi tao hoac khoi phuc don.
    public Order(String ma, Customer khachHang, ArrayList<OrderItem> danhSachMatHang) {
        if (ma == null || ma.trim().isEmpty()) throw new IllegalArgumentException("Ma don hang khong duoc de trong.");
        if (khachHang == null || danhSachMatHang == null) throw new IllegalArgumentException("Thong tin don hang chua day du.");
        this.ma = ma.trim();
        this.khachHang = khachHang;
        this.danhSachMatHang = danhSachMatHang;
    }

    // Tra ve ma don hang.
    public String layMa() { return ma; }

    // Tra ve khach hang dat don.
    public Customer layKhachHang() { return khachHang; }

    // Tra ve danh sach dong hang trong don.
    public ArrayList<OrderItem> layDanhSachMatHang() { return danhSachMatHang; }

    // Nap chong: tao dong hang tu san pham va so luong.
    public void themMatHang(Product sanPham, int soLuong) {
        danhSachMatHang.add(new OrderItem(sanPham, soLuong));
    }

    // Nap chong: them mot dong hang da co san vao don.
    public void themMatHang(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Mat hang trong don khong duoc rong.");
        danhSachMatHang.add(item);
    }

    // Cong tien cua tat ca dong hang trong don.
    public double tinhTongTien() {
        double tong = 0;
        for (OrderItem item : danhSachMatHang) tong += item.tinhThanhTien();
        return tong;
    }

    // Ghi de de hien thi tom tat don hang.
    @Override
    public String toString() {
        return ma + " | Khach hang: " + khachHang.layTen() + " | Tong tien: " + tinhTongTien();
    }

    // Ghi de so sanh hai don hang theo ma.
    @Override
    public boolean equals(Object doiTuong) {
        if (this == doiTuong) return true;
        if (!(doiTuong instanceof Order)) return false;
        return ma.equalsIgnoreCase(((Order) doiTuong).ma);
    }

    // Tao ma bam phu hop voi equals().
    @Override
    public int hashCode() { return Objects.hash(ma.toLowerCase()); }
}
