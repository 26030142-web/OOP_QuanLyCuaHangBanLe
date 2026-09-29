import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

// Lop thuc pham ke thua thong tin chung tu san pham.
public class Food extends Product {
    private static final DateTimeFormatter DINH_DANG_NGAY =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private String hanSuDung;

    // Ham tao ngan noi voi ham tao co ton kho.
    public Food(String ma, String ten, double gia, String hanSuDung) {
        this(ma, ten, gia, TON_KHO_MAC_DINH, hanSuDung);
    }

    // Ham tao day du tao thuc pham va kiem tra han su dung.
    public Food(String ma, String ten, double gia, int tonKho, String hanSuDung) {
        super(ma, ten, gia, tonKho);
        ganHanSuDung(hanSuDung);
    }

    // Tra ve han su dung.
    public String layHanSuDung() { return hanSuDung; }

    // Nhan han dung theo ngay/thang/nam va kiem tra ngay co that.
    public void ganHanSuDung(String hanSuDung) {
        if (hanSuDung == null) {
            throw new IllegalArgumentException("Han su dung khong duoc de trong.");
        }
        try {
            LocalDate.parse(hanSuDung, DINH_DANG_NGAY);
        } catch (DateTimeParseException e) {
            // Chuyen ngay theo chuan cu tu du lieu cu sang dinh dang moi.
            try {
                this.hanSuDung = LocalDate.parse(hanSuDung).format(DINH_DANG_NGAY);
                return;
            } catch (DateTimeParseException oldFormatError) {
                throw new IllegalArgumentException("Han su dung phai theo dang dd/MM/yyyy, vi du 10/12/2026.");
            }
        }
        this.hanSuDung = hanSuDung;
    }

    // Ghi de nghiep vu lay loai san pham.
    @Override
    public String layLoai() { return "Thuc pham"; }

    // Ghi de nghiep vu lay thong tin rieng cua thuc pham.
    @Override
    public String layThongTinRieng() { return "Han su dung: " + hanSuDung; }
}
