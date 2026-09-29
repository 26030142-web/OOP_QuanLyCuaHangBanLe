import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;

// Lop luu mot khoan thu hoac chi cua cua hang.
public class CashFlow {
    // Cac hang so dung de tranh nhap nham loai khoan tien.
    public static final String THU = "THU";
    public static final String CHI = "CHI";
    private static final DateTimeFormatter DINH_DANG_NGAY =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private String ma;
    private String noiDung;
    private String loai;
    private double soTien;
    private String ngay;

    // Ham tao ngan tu lay ngay hien tai.
    public CashFlow(String ma, String noiDung, String loai, double soTien) {
        this(ma, noiDung, loai, soTien, LocalDate.now().format(DINH_DANG_NGAY));
    }

    // Ham tao day du gan du lieu qua cac ham gan co kiem tra du lieu.
    public CashFlow(String ma, String noiDung, String loai, double soTien, String ngay) {
        ganMa(ma);
        ganNoiDung(noiDung);
        ganLoai(loai);
        ganSoTien(soTien);
        ganNgay(ngay);
    }

    // Tra ve ma khoan thu chi.
    public String layMa() { return ma; }

    // Tra ve noi dung khoan thu chi.
    public String layNoiDung() { return noiDung; }

    // Tra ve loai THU hoac CHI.
    public String layLoai() { return loai; }

    // Tra ve so tien.
    public double tinhThanhTien() { return soTien; }

    // Tra ve ngay theo dang dd/MM/yyyy.
    public String layNgay() { return ngay; }

    // Kiem tra ma truoc khi luu.
    public void ganMa(String ma) { this.ma = kiemTraChuoi(ma, "Ma thu chi"); }

    // Kiem tra noi dung truoc khi luu.
    public void ganNoiDung(String noiDung) { this.noiDung = kiemTraChuoi(noiDung, "Noi dung"); }

    // Chi nhan hai loai khoan tien duoc dinh nghia.
    public void ganLoai(String loai) {
        if (!THU.equalsIgnoreCase(loai) && !CHI.equalsIgnoreCase(loai)) {
            throw new IllegalArgumentException("Loai phai la THU hoac CHI.");
        }
        this.loai = loai.toUpperCase();
    }

    // So tien phai la so huu han lon hon 0.
    public void ganSoTien(double soTien) {
        if (soTien <= 0 || Double.isNaN(soTien) || Double.isInfinite(soTien)) {
            throw new IllegalArgumentException("So tien phai la so hop le lon hon 0.");
        }
        this.soTien = soTien;
    }

    // Ngay phai ton tai va dung dang ngay/thang/nam.
    public void ganNgay(String ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngay khong duoc de trong.");
        }
        try {
            LocalDate.parse(ngay, DINH_DANG_NGAY);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngay phai theo dang dd/MM/yyyy, vi du 29/09/2026.");
        }
        this.ngay = ngay;
    }

    // Kiem tra chuoi dung lam ma hoac noi dung.
    private String kiemTraChuoi(String text, String nhan) {
        if (text == null || text.trim().isEmpty() || text.contains(";")) {
            throw new IllegalArgumentException(nhan + " khong duoc de trong hoac chua dau cham phay.");
        }
        return text.trim();
    }

    // Ghi de de hien thi khoan thu chi.
    @Override
    public String toString() { return ma + " | " + loai + " | " + noiDung + " | " + soTien + " | " + ngay; }

    // Ghi de so sanh khoan thu chi theo ma.
    @Override
    public boolean equals(Object doiTuong) {
        if (this == doiTuong) return true;
        if (!(doiTuong instanceof CashFlow)) return false;
        return ma.equalsIgnoreCase(((CashFlow) doiTuong).ma);
    }

    // Tao ma bam phu hop voi equals().
    @Override
    public int hashCode() { return Objects.hash(ma.toLowerCase()); }
}
