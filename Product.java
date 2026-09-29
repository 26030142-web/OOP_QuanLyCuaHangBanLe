import java.util.Objects;

// Lop cha truu tuong luu nhung thong tin chung cua moi san pham.
public abstract class Product {
    // Hang so dung khi san pham moi chua co hang trong kho.
    public static final int TON_KHO_MAC_DINH = 0;

    // Cac thuoc tinh duoc dong goi de chi thay doi qua ham lay va ham gan.
    private String ma;
    private String ten;
    private double gia;
    private int tonKho;

    // Ham tao ngan goi ham tao day du voi ton kho mac dinh.
    public Product(String ma, String ten, double gia) {
        this(ma, ten, gia, TON_KHO_MAC_DINH);
    }

    // Ham tao day du gan du lieu thong qua ham gan co kiem tra.
    public Product(String ma, String ten, double gia, int tonKho) {
        ganMa(ma);
        ganTen(ten);
        ganGia(gia);
        ganTonKho(tonKho);
    }

    // Tra ve ma san pham.
    public String layMa() { return ma; }

    // Tra ve ten san pham.
    public String layTen() { return ten; }

    // Tra ve gia ban.
    public double layGia() { return gia; }

    // Tra ve so luong ton kho.
    public int layTonKho() { return tonKho; }

    // Gan ma va tu choi ma rong hoac chua dau phan cach tep phan cach.
    public void ganMa(String ma) {
        if (ma == null || ma.trim().isEmpty() || ma.contains(";")) {
            throw new IllegalArgumentException("Ma san pham khong duoc de trong hoac chua dau cham phay.");
        }
        this.ma = ma.trim();
    }

    // Gan ten va kiem tra du lieu truoc khi luu.
    public void ganTen(String ten) {
        if (ten == null || ten.trim().isEmpty() || ten.contains(";")) {
            throw new IllegalArgumentException("Ten san pham khong duoc de trong hoac chua dau cham phay.");
        }
        this.ten = ten.trim();
    }

    // Chi nhan gia huu han va lon hon 0.
    public void ganGia(double gia) {
        if (gia <= 0 || Double.isNaN(gia) || Double.isInfinite(gia)) {
            throw new IllegalArgumentException("Gia phai la so hop le lon hon 0.");
        }
        this.gia = gia;
    }

    // Ton kho khong duoc am.
    public void ganTonKho(int tonKho) {
        if (tonKho < 0) {
            throw new IllegalArgumentException("Ton kho khong duoc am.");
        }
        this.tonKho = tonKho;
    }

    // Lop con tra ve ten loai san pham.
    public abstract String layLoai();

    // Lop con tra ve thong tin rieng nhu han dung hoac kich co.
    public abstract String layThongTinRieng();

    // Ghi de de hien thi moi thong tin san pham trong mot dong.
    @Override
    public String toString() {
        return ma + " | " + ten + " | " + gia + " | ton kho: " + tonKho
                + " | " + layLoai() + " | " + layThongTinRieng();
    }

    // Hai san pham duoc xem la giong nhau neu cung ma.
    @Override
    public boolean equals(Object doiTuong) {
        if (this == doiTuong) return true;
        if (!(doiTuong instanceof Product)) return false;
        Product khac = (Product) doiTuong;
        return ma.equalsIgnoreCase(khac.ma);
    }

    // Tao ma bam tu ma de phu hop voi equals().
    @Override
    public int hashCode() {
        return Objects.hash(ma.toLowerCase());
    }
}
