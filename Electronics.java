// Lop dien tu ke thua thong tin chung tu san pham.
public class Electronics extends Product {
    private int thangBaoHanh;

    // Ham tao ngan noi voi ham tao co ton kho.
    public Electronics(String ma, String ten, double gia, int thangBaoHanh) {
        this(ma, ten, gia, TON_KHO_MAC_DINH, thangBaoHanh);
    }

    // Ham tao day du tao san pham dien tu.
    public Electronics(String ma, String ten, double gia, int tonKho, int thangBaoHanh) {
        super(ma, ten, gia, tonKho);
        ganThangBaoHanh(thangBaoHanh);
    }

    // Tra ve so thang bao hanh.
    public int layThangBaoHanh() { return thangBaoHanh; }

    // Thang bao hanh khong duoc am.
    public void ganThangBaoHanh(int thangBaoHanh) {
        if (thangBaoHanh < 0) {
            throw new IllegalArgumentException("So thang bao hanh khong duoc am.");
        }
        this.thangBaoHanh = thangBaoHanh;
    }

    // Ghi de nghiep vu lay loai san pham.
    @Override
    public String layLoai() { return "Dien tu"; }

    // Ghi de nghiep vu lay thong tin rieng cua do dien tu.
    @Override
    public String layThongTinRieng() { return "Bao hanh: " + thangBaoHanh + " thang"; }
}
