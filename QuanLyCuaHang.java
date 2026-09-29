import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Lop quan ly danh sach va nghiep vu; mau doi tuong duy nhat bao dam chi co mot cua hang.
public class RetailStore implements Searchable<Product>, DataStorage {
    // Mot doi tuong duy nhat duoc dung xuyen suot chuong trinh.
    private static final RetailStore INSTANCE = new RetailStore();
    private static final String THU_MUC_DU_LIEU = "data";

    // Cac danh sach dung kieu cha san pham de chua nhieu lop san pham con.
    private ArrayList<Product> danhSachSanPham = new ArrayList<Product>();
    private ArrayList<Customer> danhSachKhachHang = new ArrayList<Customer>();
    private ArrayList<Order> danhSachDonHang = new ArrayList<Order>();
    private ArrayList<CashFlow> danhSachThuChi = new ArrayList<CashFlow>();

    // Ham tao rieng de noi khac khong tao them cua hang.
    private RetailStore() { }

    // Tra ve doi tuong mau doi tuong duy nhat duy nhat.
    public static RetailStore layInstance() { return INSTANCE; }

    // Tra ve danh sach san pham cho menu hien thi va thong ke.
    public ArrayList<Product> layDanhSachSanPham() { return danhSachSanPham; }

    // Tra ve danh sach khach hang cho menu hien thi va thong ke.
    public ArrayList<Customer> layDanhSachKhachHang() { return danhSachKhachHang; }

    // Tra ve danh sach don hang cho menu hien thi va thong ke.
    public ArrayList<Order> layDanhSachDonHang() { return danhSachDonHang; }

    // Tra ve danh sach thu chi cho menu hien thi va thong ke.
    public ArrayList<CashFlow> layDanhSachThuChi() { return danhSachThuChi; }

    // ngoai le rieng bao khong tim thay ma san pham.
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) { super(message); }
    }

    // ngoai le rieng boc loi doc hoac ghi file.
    public static class DataStorageException extends RuntimeException {
        public DataStorageException(String message, Throwable cause) { super(message, cause); }
    }

    // Tim san pham theo ma hoac bao ngoai le neu khong co.
    public Product timSanPhamTheoMa(String ma) {
        for (Product sanPham : danhSachSanPham) {
            if (sanPham.layMa().equalsIgnoreCase(ma)) return sanPham;
        }
        throw new ProductNotFoundException("Khong tim thay san pham: " + ma);
    }

    // Tim khach hang theo ma.
    public Customer timKhachHangTheoMa(String ma) {
        for (Customer khachHang : danhSachKhachHang) {
            if (khachHang.layMa().equalsIgnoreCase(ma)) return khachHang;
        }
        throw new IllegalArgumentException("Khong tim thay khach hang: " + ma);
    }

    // Tim don hang theo ma.
    public Order timDonHangTheoMa(String ma) {
        for (Order order : danhSachDonHang) {
            if (order.layMa().equalsIgnoreCase(ma)) return order;
        }
        throw new IllegalArgumentException("Khong tim thay don hang: " + ma);
    }

    // Tim khoan thu chi theo ma.
    public CashFlow timThuChiTheoMa(String ma) {
        for (CashFlow cashFlow : danhSachThuChi) {
            if (cashFlow.layMa().equalsIgnoreCase(ma)) return cashFlow;
        }
        throw new IllegalArgumentException("Khong tim thay khoan thu chi: " + ma);
    }

    // Them san pham va tu choi ma trung.
    public void themSanPham(Product sanPham) {
        for (Product cu : danhSachSanPham) {
            if (cu.equals(sanPham)) throw new IllegalArgumentException("Ma san pham da ton tai.");
        }
        danhSachSanPham.add(sanPham);
    }

    // Xoa san pham tim duoc theo ma.
    public void xoaSanPham(String ma) { danhSachSanPham.remove(timSanPhamTheoMa(ma)); }

    // Tim san pham theo tu khoa va chuyen sang ham tim co loc loai.
    @Override
    public ArrayList<Product> timKiem(String tuKhoa) { return timKiem(tuKhoa, ""); }

    // Nap chong: tim theo ma/ten, co the gioi han theo danh muc.
    public ArrayList<Product> timKiem(String tuKhoa, String loai) {
        ArrayList<Product> ketQua = new ArrayList<Product>();
        for (Product sanPham : danhSachSanPham) {
            boolean khopChu = sanPham.layMa().toLowerCase().contains(tuKhoa.toLowerCase())
                    || sanPham.layTen().toLowerCase().contains(tuKhoa.toLowerCase());
            boolean khopLoai = loai.isEmpty()
                    || sanPham.layLoai().equalsIgnoreCase(loai);
            if (khopChu && khopLoai) ketQua.add(sanPham);
        }
        return ketQua;
    }

    // Them khach hang va tu choi ma trung.
    public void themKhachHang(Customer khachHang) {
        for (Customer cu : danhSachKhachHang) {
            if (cu.equals(khachHang)) throw new IllegalArgumentException("Ma khach hang da ton tai.");
        }
        danhSachKhachHang.add(khachHang);
    }

    // Khong xoa khach da co don de giu lien ket voi lich su ban hang.
    public void xoaKhachHang(String ma) {
        Customer khachHang = timKhachHangTheoMa(ma);
        for (Order order : danhSachDonHang) {
            if (order.layKhachHang().equals(khachHang)) {
                throw new IllegalArgumentException("Khong the xoa khach hang da co don hang.");
            }
        }
        danhSachKhachHang.remove(khachHang);
    }

    // Tim khach hang theo ma hoac ten.
    public ArrayList<Customer> timKiemKhachHang(String tuKhoa) {
        ArrayList<Customer> ketQua = new ArrayList<Customer>();
        for (Customer khachHang : danhSachKhachHang) {
            if (khachHang.layMa().toLowerCase().contains(tuKhoa.toLowerCase())
                    || khachHang.layTen().toLowerCase().contains(tuKhoa.toLowerCase())) ketQua.add(khachHang);
        }
        return ketQua;
    }

    // Them don hang va tu choi ma don trung.
    public void themDonHang(Order order) {
        for (Order cu : danhSachDonHang) {
            if (cu.equals(order)) throw new IllegalArgumentException("Ma don hang da ton tai.");
        }
        danhSachDonHang.add(order);
    }

    // Xoa don hang theo ma.
    public void xoaDonHang(String ma) { danhSachDonHang.remove(timDonHangTheoMa(ma)); }

    // Tim don hang theo ma don hoac ten khach hang.
    public ArrayList<Order> timKiemDonHang(String tuKhoa) {
        ArrayList<Order> ketQua = new ArrayList<Order>();
        for (Order order : danhSachDonHang) {
            if (order.layMa().toLowerCase().contains(tuKhoa.toLowerCase())
                    || order.layKhachHang().layTen().toLowerCase().contains(tuKhoa.toLowerCase())) ketQua.add(order);
        }
        return ketQua;
    }

    // Them khoan thu chi va tu choi ma trung.
    public void themThuChi(CashFlow cashFlow) {
        for (CashFlow cu : danhSachThuChi) {
            if (cu.equals(cashFlow)) throw new IllegalArgumentException("Ma thu chi da ton tai.");
        }
        danhSachThuChi.add(cashFlow);
    }

    // Xoa khoan thu chi theo ma.
    public void xoaThuChi(String ma) { danhSachThuChi.remove(timThuChiTheoMa(ma)); }

    // Tim khoan thu chi theo ma hoac noi dung.
    public ArrayList<CashFlow> timKiemThuChi(String tuKhoa) {
        ArrayList<CashFlow> ketQua = new ArrayList<CashFlow>();
        for (CashFlow cashFlow : danhSachThuChi) {
            if (cashFlow.layMa().toLowerCase().contains(tuKhoa.toLowerCase())
                    || cashFlow.layNoiDung().toLowerCase().contains(tuKhoa.toLowerCase())) ketQua.add(cashFlow);
        }
        return ketQua;
    }

    // Cong doanh thu tu tong tien cac don hang.
    public double tinhDoanhThu() {
        double tong = 0;
        for (Order order : danhSachDonHang) tong += order.tinhTongTien();
        return tong;
    }

    // Cong cac khoan theo loai THU hoac CHI.
    public double tinhTongThuChi(String loai) {
        double tong = 0;
        for (CashFlow cashFlow : danhSachThuChi) {
            if (cashFlow.layLoai().equalsIgnoreCase(loai)) tong += cashFlow.tinhThanhTien();
        }
        return tong;
    }

    // Tao duong dan file nam trong thu muc du lieu cua chuong trinh.
    private String layDuongDan(String tenTep) { return THU_MUC_DU_LIEU + "/" + tenTep; }

    // Ghi danh sach dong vao file UTF-8 va tu tao thu muc neu chua co.
    private void ghiDanhSachDong(String tenTep, List<String> lines) throws IOException {
        Files.createDirectories(Paths.get(THU_MUC_DU_LIEU));
        Files.write(Paths.get(layDuongDan(tenTep)), lines, StandardCharsets.UTF_8);
    }

    // Doc file UTF-8; file chua ton tai duoc xem la danh sach rong.
    private List<String> docDanhSachDong(String tenTep) throws IOException {
        if (!Files.exists(Paths.get(layDuongDan(tenTep)))) return new ArrayList<String>();
        return Files.readAllLines(Paths.get(layDuongDan(tenTep)), StandardCharsets.UTF_8);
    }

    // Ghi ca bon nhom va chi tiet don hang xuong cac file tep phan cach.
    @Override
    public void luuDuLieu() {
        try {
            ArrayList<String> cacDong = new ArrayList<String>();
            for (Product sanPham : danhSachSanPham) {
                cacDong.add(sanPham.layMa()+";"+sanPham.layTen()+";"+sanPham.layGia()+";"+sanPham.layTonKho()+";"+sanPham.layLoai()+";"+layThongTinRieng(sanPham));
            }
            ghiDanhSachDong("products.csv", cacDong);

            cacDong = new ArrayList<String>();
            for (Customer khachHang : danhSachKhachHang) cacDong.add(khachHang.layMa()+";"+khachHang.layTen()+";"+khachHang.laySoDienThoai());
            ghiDanhSachDong("customers.csv", cacDong);

            cacDong = new ArrayList<String>();
            for (Order order : danhSachDonHang) cacDong.add(order.layMa()+";"+order.layKhachHang().layMa());
            ghiDanhSachDong("orders.csv", cacDong);

            cacDong = new ArrayList<String>();
            for (Order order : danhSachDonHang) {
                for (OrderItem item : order.layDanhSachMatHang()) cacDong.add(order.layMa()+";"+item.layTenSanPham()+";"+item.layDonGia()+";"+item.laySoLuong());
            }
            ghiDanhSachDong("order_items.csv", cacDong);

            cacDong = new ArrayList<String>();
            for (CashFlow cashFlow : danhSachThuChi) cacDong.add(cashFlow.layMa()+";"+cashFlow.layNoiDung()+";"+cashFlow.layLoai()+";"+cashFlow.tinhThanhTien()+";"+cashFlow.layNgay());
            ghiDanhSachDong("cash_flows.csv", cacDong);
            // Xoa file ten cu sau khi du lieu da duoc chuyen sang ten moi.
            Files.deleteIfExists(Paths.get(layDuongDan("thuchi.csv")));
        } catch (IOException e) {
            throw new DataStorageException("Khong the luu tep du lieu.", e);
        }
    }

    // Lay du lieu rieng cua tung lop con de luu cung san pham.
    private String layThongTinRieng(Product sanPham) {
        if (sanPham instanceof Food) return ((Food) sanPham).layHanSuDung();
        if (sanPham instanceof Electronics) return String.valueOf(((Electronics) sanPham).layThangBaoHanh());
        return ((Clothing) sanPham).layKichCo();
    }

    // Doc tep phan cach theo thu tu san pham, khach hang, don, dong don va thu chi.
    @Override
    public void docDuLieu() {
        try {
            danhSachSanPham.clear(); danhSachKhachHang.clear(); danhSachDonHang.clear(); danhSachThuChi.clear();
            for (String row : docDanhSachDong("products.csv")) {
                String[] duLieu = row.split(";", -1);
                themSanPham(ProductFactory.tao(duLieu[4], duLieu[0], duLieu[1], Double.parseDouble(duLieu[2]), Integer.parseInt(duLieu[3]), duLieu[5]));
            }
            for (String row : docDanhSachDong("customers.csv")) {
                String[] duLieu = row.split(";", -1);
                themKhachHang(new Customer(duLieu[0], duLieu[1], duLieu[2]));
            }
            for (String row : docDanhSachDong("orders.csv")) {
                String[] duLieu = row.split(";", -1);
                themDonHang(new Order(duLieu[0], timKhachHangTheoMa(duLieu[1])));
            }
            for (String row : docDanhSachDong("order_items.csv")) {
                String[] duLieu = row.split(";", -1);
                timDonHangTheoMa(duLieu[0]).themMatHang(new OrderItem(duLieu[1], Double.parseDouble(duLieu[2]), Integer.parseInt(duLieu[3])));
            }
            // Neu chua co file moi thi doc file thu chi cua ban cu de chuyen doi.
            List<String> savedCashFlows = docDanhSachDong("cash_flows.csv");
            boolean loadOldCashFlowFile = savedCashFlows.isEmpty()
                    && Files.exists(Paths.get(layDuongDan("thuchi.csv")));
            if (loadOldCashFlowFile) savedCashFlows = docDanhSachDong("thuchi.csv");
            for (String row : savedCashFlows) {
                String[] duLieu = row.split(";", -1);
                String loai = duLieu[2];
                String ngay = duLieu[4];
                // Doi ma loai thu chi va ngay cua du lieu cu sang chuan hien tai.
                if (loadOldCashFlowFile) {
                    if (loai.equalsIgnoreCase("THU") || loai.equalsIgnoreCase("INCOME")) loai = CashFlow.THU;
                    if (loai.equalsIgnoreCase("CHI") || loai.equalsIgnoreCase("EXPENSE")) loai = CashFlow.CHI;
                    ngay = java.time.LocalDate.parse(ngay).format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                }
                themThuChi(new CashFlow(duLieu[0], duLieu[1], loai, Double.parseDouble(duLieu[3]), ngay));
            }
        } catch (IOException | RuntimeException e) {
            throw new DataStorageException("Khong the doc du lieu. Hay kiem tra cac tep CSV trong thu muc data.", e);
        }
    }
}
