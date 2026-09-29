import java.util.Objects;

// Lop luu thong tin mot khach hang.
public class Customer {
    private String ma;
    private String ten;
    private String soDienThoai;

    // Ham tao ngan dung gia tri mac dinh cho so dien thoai.
    public Customer(String ma, String ten) {
        this(ma, ten, "Khong co");
    }

    // Ham tao day du gan thong tin qua cac ham gan.
    public Customer(String ma, String ten, String soDienThoai) {
        ganMa(ma);
        ganTen(ten);
        ganSoDienThoai(soDienThoai);
    }

    // Tra ve ma khach hang.
    public String layMa() { return ma; }

    // Tra ve ten khach hang.
    public String layTen() { return ten; }

    // Tra ve so dien thoai.
    public String laySoDienThoai() { return soDienThoai; }

    // Kiem tra ma khach hang truoc khi gan.
    public void ganMa(String ma) {
        if (ma == null || ma.trim().isEmpty() || ma.contains(";")) {
            throw new IllegalArgumentException("Ma khach hang khong duoc de trong hoac chua dau cham phay.");
        }
        this.ma = ma.trim();
    }

    // Kiem tra ten khach hang truoc khi gan.
    public void ganTen(String ten) {
        if (ten == null || ten.trim().isEmpty() || ten.contains(";")) {
            throw new IllegalArgumentException("Ten khach hang khong duoc de trong hoac chua dau cham phay.");
        }
        this.ten = ten.trim();
    }

    // Kiem tra so dien thoai truoc khi gan.
    public void ganSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.trim().isEmpty() || soDienThoai.contains(";")) {
            throw new IllegalArgumentException("So dien thoai khong duoc de trong hoac chua dau cham phay.");
        }
        this.soDienThoai = soDienThoai.trim();
    }

    // Ghi de de hien thi khach hang.
    @Override
    public String toString() { return ma + " | " + ten + " | " + soDienThoai; }

    // Ghi de so sanh khach hang theo ma.
    @Override
    public boolean equals(Object doiTuong) {
        if (this == doiTuong) return true;
        if (!(doiTuong instanceof Customer)) return false;
        return ma.equalsIgnoreCase(((Customer) doiTuong).ma);
    }

    // Tao ma bam phu hop voi cach so sanh equals().
    @Override
    public int hashCode() { return Objects.hash(ma.toLowerCase()); }
}
