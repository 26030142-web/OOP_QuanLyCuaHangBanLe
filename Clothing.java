// Lop quan ao ke thua thong tin chung tu san pham.
public class Clothing extends Product {
    private String kichCo;

    // Ham tao ngan noi voi ham tao co ton kho.
    public Clothing(String ma, String ten, double gia, String kichCo) {
        this(ma, ten, gia, TON_KHO_MAC_DINH, kichCo);
    }

    // Ham tao day du tao san pham quan ao.
    public Clothing(String ma, String ten, double gia, int tonKho, String kichCo) {
        super(ma, ten, gia, tonKho);
        ganKichCo(kichCo);
    }

    // Tra ve kich co quan ao.
    public String layKichCo() { return kichCo; }

    // Kich co khong duoc de trong hoac chua dau phan cach tep phan cach.
    public void ganKichCo(String kichCo) {
        if (kichCo == null || kichCo.trim().isEmpty() || kichCo.contains(";")) {
            throw new IllegalArgumentException("Kich co khong duoc de trong hoac chua dau cham phay.");
        }
        this.kichCo = kichCo.trim();
    }

    // Ghi de nghiep vu lay loai san pham.
    @Override
    public String layLoai() { return "Quan ao"; }

    // Ghi de nghiep vu lay thong tin rieng cua quan ao.
    @Override
    public String layThongTinRieng() { return "Kich co: " + kichCo; }
}
