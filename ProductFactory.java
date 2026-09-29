// Lop tao lop san pham con theo loai nguoi dung chon.
public class ProductFactory {
    // Tao ThucPham, DienTu hoac QuanAo voi cac du lieu dau vao.
    public static Product tao(String loai, String ma, String ten,
                                 double gia, int tonKho, String thongTinRieng) {
        if (loai == null) throw new IllegalArgumentException("Hay chon loai san pham.");
        switch (loai.toLowerCase()) {
            case "thucpham":
            case "thuc pham":
            case "food":
                return new Food(ma, ten, gia, tonKho, thongTinRieng);
            case "dientu":
            case "dien tu":
            case "electronics":
                return new Electronics(ma, ten, gia, tonKho, Integer.parseInt(thongTinRieng));
            case "quanao":
            case "quan ao":
            case "clothing":
                return new Clothing(ma, ten, gia, tonKho, thongTinRieng);
            default:
                throw new IllegalArgumentException("Loai san pham khong hop le.");
        }
    }
}
