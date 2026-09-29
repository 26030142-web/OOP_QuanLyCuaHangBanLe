import java.util.Scanner;

// Lop chay chuong trinh, hien thi menu va nhan du lieu tu ban phim.
public class Main {
    // Doi tuong dung chung de doc du lieu tu ban phim.
    private static final Scanner input = new Scanner(System.in);
    // Lay doi tuong cua hang duy nhat.
    private static final RetailStore store = RetailStore.layInstance();

    // Nap tep phan cach truoc khi mo menu; luu lai du lieu khi nguoi dung thoat.
    public static void main(String[] args) {
        try {
            store.docDuLieu();
        } catch (RetailStore.DataStorageException e) {
            System.out.println(e.getMessage());
        }
        int luaChon;
        do {
            hienThiMenuChinh();
            luaChon = nhapSoNguyen("Chon chuc nang: ", 0, 4);
            try {
                if (luaChon == 1)
                    menuSanPham();
                else if (luaChon == 2)
                    menuKhachHang();
                else if (luaChon == 3)
                    menuDonHang();
                else if (luaChon == 4)
                    menuThuChi();
                else if (luaChon == 0) {
                    store.luuDuLieu();
                    System.out.println("Da luu du lieu. Tam biet!");
                }
            } catch (RuntimeException e) {
                System.out.println("Thao tac that bai: " + e.getMessage());
            }
        } while (luaChon != 0);
        input.close();
    }

    // In menu chinh voi khung ky tu de hien thi on dinh trong cua so dong lenh.
    private static void hienThiMenuChinh() {
        System.out.println();
        System.out.println("+------------------------------------------------+");
        System.out.println("|          QUAN LY CUA HANG BAN LE               |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|  1. Quan ly san pham                           |");
        System.out.println("|  2. Quan ly khach hang                         |");
        System.out.println("|  3. Quan ly don hang                           |");
        System.out.println("|  4. Quan ly thu chi                            |");
        System.out.println("|  0. Luu va thoat                               |");
        System.out.println("+------------------------------------------------+");
    }

    // Nhan chuoi khong rong, dong thoi chan dau cham phay cua file tep phan cach.
    private static String nhapChuoi(String nhan) {
        while (true) {
            System.out.print(nhan);
            String giaTri = input.nextLine().trim();
            if (!giaTri.isEmpty() && !giaTri.contains(";"))
                return giaTri;
            System.out.println("Khong duoc de trong hoac chua dau ';'. Hay nhap lai.");
        }
    }

    // Nhan so nguyen trong khoang cho phep va hoi lai dung truong neu sai.
    private static int nhapSoNguyen(String nhan, int min, int max) {
        while (true) {
            System.out.print(nhan);
            try {
                int giaTri = Integer.parseInt(input.nextLine().trim());
                if (giaTri >= min && giaTri <= max)
                    return giaTri;
            } catch (NumberFormatException e) {
                System.out.println("Hay nhap so nguyen.");
            }
            System.out.println("Hay nhap so trong khoang " + min + " den " + max + ".");
        }
    }

    // Nhan so luong lon hon 0.
    private static int nhapSoDuong(String nhan) {
        return nhapSoNguyen(nhan, 1, Integer.MAX_VALUE);
    }

    // Nhan so tien hop le va lon hon 0.
    private static double nhapSoTien(String nhan) {
        while (true) {
            System.out.print(nhan);
            try {
                double giaTri = Double.parseDouble(input.nextLine().trim());
                if (giaTri > 0 && !Double.isInfinite(giaTri) && !Double.isNaN(giaTri))
                    return giaTri;
            } catch (NumberFormatException e) {
                System.out.println("Hay nhap mot so hop le.");
            }
            System.out.println("Gia tri phai lon hon 0. Hay nhap lai.");
        }
    }

    // Nhan ngay co that theo dang ngay/thang/nam.
    private static String nhapNgay(String nhan) {
        while (true) {
            String ngay = nhapChuoi(nhan);
            try {
                java.time.LocalDate.parse(ngay,
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
                                .withResolverStyle(java.time.format.ResolverStyle.STRICT));
                return ngay;
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Ngay khong hop le. Dung dd/MM/yyyy, vi du 10/12/2026. Hay nhap lai.");
            }
        }
    }

    // In tung phan tu cua danh sach; bao ro khi danh sach chua co du lieu.
    private static void hienThiDanhSach(Iterable<?> danhSachMatHang) {
        boolean empty = true;
        for (Object item : danhSachMatHang) {
            System.out.println("  " + item);
            empty = false;
        }
        if (empty)
            System.out.println("Khong co du lieu.");
    }

    // Luu thay doi ngay sau thao tac them, sua hoac xoa.
    private static void luuSauKhiThayDoi() {
        store.luuDuLieu();
    }

    // Doc ma moi chua duoc dung cho san pham.
    private static String nhapMaSanPhamMoi() {
        while (true) {
            String ma = nhapChuoi("Ma san pham: ");
            boolean tonTai = false;
            for (Product sanPham : store.layDanhSachSanPham()) {
                if (sanPham.layMa().equalsIgnoreCase(ma))
                    tonTai = true;
            }
            if (!tonTai)
                return ma;
            System.out.println("Ma san pham da ton tai. Hay nhap ma khac.");
        }
    }

    // Doc ma moi chua duoc dung cho khach hang.
    private static String nhapMaKhachHangMoi() {
        while (true) {
            String ma = nhapChuoi("Ma khach hang: ");
            boolean tonTai = false;
            for (Customer khachHang : store.layDanhSachKhachHang()) {
                if (khachHang.layMa().equalsIgnoreCase(ma))
                    tonTai = true;
            }
            if (!tonTai)
                return ma;
            System.out.println("Ma khach hang da ton tai. Hay nhap ma khac.");
        }
    }

    // Doc ma moi chua duoc dung cho don hang.
    private static String nhapMaDonHangMoi() {
        while (true) {
            String ma = nhapChuoi("Ma don hang: ");
            boolean tonTai = false;
            for (Order order : store.layDanhSachDonHang()) {
                if (order.layMa().equalsIgnoreCase(ma))
                    tonTai = true;
            }
            if (!tonTai)
                return ma;
            System.out.println("Ma don hang da ton tai. Hay nhap ma khac.");
        }
    }

    // Doc ma moi chua duoc dung cho khoan thu chi.
    private static String nhapMaThuChiMoi() {
        while (true) {
            String ma = nhapChuoi("Ma thu chi: ");
            boolean tonTai = false;
            for (CashFlow item : store.layDanhSachThuChi()) {
                if (item.layMa().equalsIgnoreCase(ma))
                    tonTai = true;
            }
            if (!tonTai)
                return ma;
            System.out.println("Ma thu chi da ton tai. Hay nhap ma khac.");
        }
    }

    // Tim san pham; neu ma chua co thi hoi lai ma ngay tai truong nay.
    private static Product nhapSanPhamDaCo() {
        while (true) {
            String ma = nhapChuoi("Ma san pham: ");
            try {
                return store.timSanPhamTheoMa(ma);
            } catch (RetailStore.ProductNotFoundException e) {
                System.out.println(e.getMessage() + ". Hay nhap lai ma.");
            }
        }
    }

    // Tim khach hang; neu ma chua co thi hoi lai ma ngay tai truong nay.
    private static Customer nhapKhachHangDaCo() {
        while (true) {
            String ma = nhapChuoi("Ma khach hang: ");
            try {
                return store.timKhachHangTheoMa(ma);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Hay nhap lai ma.");
            }
        }
    }

    // Tim don hang; neu ma chua co thi hoi lai ma ngay tai truong nay.
    private static Order nhapDonHangDaCo() {
        while (true) {
            String ma = nhapChuoi("Ma don hang: ");
            try {
                return store.timDonHangTheoMa(ma);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Hay nhap lai ma.");
            }
        }
    }

    // Tim khoan thu chi; neu ma chua co thi hoi lai ma ngay tai truong nay.
    private static CashFlow nhapThuChiDaCo() {
        while (true) {
            String ma = nhapChuoi("Ma thu chi: ");
            try {
                return store.timThuChiTheoMa(ma);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Hay nhap lai ma.");
            }
        }
    }

    // Nhap cac truong san pham; khi sua thi giu nguyen loai san pham.
    private static Product nhapThongTinSanPham(String ma, int tonKho, String loaiHienTai) {
        String ten = nhapChuoi("Ten san pham: ");
        double gia = nhapSoTien("Gia ban: ");
        String tenLoai;
        String thongTinRieng;
        int maLoai;
        if (loaiHienTai == null) {
            System.out.println("  1. Thuc pham\n  2. Dien tu\n  3. Quan ao");
            maLoai = nhapSoNguyen("Loai san pham: ", 1, 3);
        } else if (loaiHienTai.equalsIgnoreCase("Thuc pham") || loaiHienTai.equalsIgnoreCase("ThucPham")
                || loaiHienTai.equalsIgnoreCase("Food"))
            maLoai = 1;
        else if (loaiHienTai.equalsIgnoreCase("Dien tu") || loaiHienTai.equalsIgnoreCase("DienTu")
                || loaiHienTai.equalsIgnoreCase("Electronics"))
            maLoai = 2;
        else
            maLoai = 3;
        if (maLoai == 1) {
            tenLoai = "thucpham";
            thongTinRieng = nhapNgay("Han su dung (dd/MM/yyyy): ");
        } else if (maLoai == 2) {
            tenLoai = "dientu";
            thongTinRieng = String.valueOf(nhapSoNguyen("So thang bao hanh: ", 0, 100));
        } else {
            tenLoai = "quanao";
            thongTinRieng = nhapChuoi("Kich co quan ao: ");
        }
        if (loaiHienTai != null)
            tonKho = nhapSoNguyen("So luong ton kho: ", 0, Integer.MAX_VALUE);
        return ProductFactory.tao(tenLoai, ma, ten, gia, tonKho, thongTinRieng);
    }

    // Hien thi menu san pham va goi chuc nang tuong ung.
    private static void menuSanPham() {
        int luaChon;
        do {
            System.out.println("\n+----------------- QUAN LY SAN PHAM -----------------+");
            System.out.println("| 1. Them san pham   2. Sua san pham/ton kho         |");
            System.out.println("| 3. Xoa san pham    4. Tim san pham                 |");
            System.out.println("| 5. Thong ke        6. Xem tat ca                   |");
            System.out.println("| 0. Quay lai                                        |");
            System.out.println("+-----------------------------------------------------+");
            luaChon = nhapSoNguyen("Chon chuc nang: ", 0, 6);
            try {
                if (luaChon == 1) {
                    Product sanPham = nhapThongTinSanPham(nhapMaSanPhamMoi(), Product.TON_KHO_MAC_DINH, null);
                    store.themSanPham(sanPham);
                    luuSauKhiThayDoi();
                    System.out.println("Da them san pham.");
                } else if (luaChon == 2) {
                    Product cu = nhapSanPhamDaCo();
                    Product updated = nhapThongTinSanPham(cu.layMa(), cu.layTonKho(), cu.layLoai());
                    cu.ganTen(updated.layTen());
                    cu.ganGia(updated.layGia());
                    cu.ganTonKho(updated.layTonKho());
                    if (cu instanceof Food)
                        ((Food) cu).ganHanSuDung(((Food) updated).layHanSuDung());
                    if (cu instanceof Electronics)
                        ((Electronics) cu).ganThangBaoHanh(((Electronics) updated).layThangBaoHanh());
                    if (cu instanceof Clothing)
                        ((Clothing) cu).ganKichCo(((Clothing) updated).layKichCo());
                    luuSauKhiThayDoi();
                    System.out.println("Da cap nhat san pham.");
                } else if (luaChon == 3) {
                    store.xoaSanPham(nhapSanPhamDaCo().layMa());
                    luuSauKhiThayDoi();
                    System.out.println("Da xoa san pham.");
                } else if (luaChon == 4) {
                    String tuKhoa = nhapChuoi("Nhap ma hoac ten san pham: ");
                    hienThiDanhSach(store.timKiem(tuKhoa));
                    System.out.println("So ket qua tim thay: " + store.coKetQua(tuKhoa));
                } else if (luaChon == 5) {
                    int thucPham = 0, dienTu = 0, quanAo = 0, totalStock = 0;
                    for (Product sanPham : store.layDanhSachSanPham()) {
                        if (sanPham instanceof Food)
                            thucPham++;
                        else if (sanPham instanceof Electronics)
                            dienTu++;
                        else if (sanPham instanceof Clothing)
                            quanAo++;
                        totalStock += sanPham.layTonKho();
                    }
                    System.out.println("Thuc pham: " + thucPham + " | Dien tu: " + dienTu + " | Quan ao: " + quanAo);
                    System.out.println(
                            "So san pham: " + store.layDanhSachSanPham().size() + " | Tong ton kho: " + totalStock);
                } else if (luaChon == 6)
                    hienThiDanhSach(store.layDanhSachSanPham());
            } catch (RuntimeException e) {
                System.out.println("Khong the thuc hien thao tac san pham: " + e.getMessage());
            }
        } while (luaChon != 0);
    }

    // Hien thi menu khach hang va xu ly du chuc nang quan ly.
    private static void menuKhachHang() {
        int luaChon;
        do {
            System.out.println("\n+---------------- QUAN LY KHACH HANG ----------------+");
            System.out.println("| 1. Them khach hang 2. Sua khach hang               |");
            System.out.println("| 3. Xoa khach hang  4. Tim khach hang               |");
            System.out.println("| 5. Thong ke        6. Xem tat ca                   |");
            System.out.println("| 0. Quay lai                                        |");
            System.out.println("+-----------------------------------------------------+");
            luaChon = nhapSoNguyen("Chon chuc nang: ", 0, 6);
            try {
                if (luaChon == 1) {
                    Customer khachHang = new Customer(nhapMaKhachHangMoi(), nhapChuoi("Ten khach hang: "),
                            nhapChuoi("So dien thoai: "));
                    store.themKhachHang(khachHang);
                    luuSauKhiThayDoi();
                    System.out.println("Da them khach hang.");
                } else if (luaChon == 2) {
                    Customer khachHang = nhapKhachHangDaCo();
                    khachHang.ganTen(nhapChuoi("Ten moi: "));
                    khachHang.ganSoDienThoai(nhapChuoi("So dien thoai moi: "));
                    luuSauKhiThayDoi();
                    System.out.println("Da cap nhat khach hang.");
                } else if (luaChon == 3) {
                    store.xoaKhachHang(nhapKhachHangDaCo().layMa());
                    luuSauKhiThayDoi();
                    System.out.println("Da xoa khach hang.");
                } else if (luaChon == 4) {
                    hienThiDanhSach(store.timKiemKhachHang(nhapChuoi("Nhap ma hoac ten khach hang: ")));
                } else if (luaChon == 5) {
                    System.out.println("Tong so khach hang: " + store.layDanhSachKhachHang().size());
                } else if (luaChon == 6)
                    hienThiDanhSach(store.layDanhSachKhachHang());
            } catch (RuntimeException e) {
                System.out.println("Khong the thuc hien thao tac khach hang: " + e.getMessage());
            }
        } while (luaChon != 0);
    }

    // Hien thi menu don hang va quan ly cac dong san pham trong don.
    private static void menuDonHang() {
        int luaChon;
        do {
            System.out.println("\n+------------------ QUAN LY DON HANG ----------------+");
            System.out.println("| 1. Them don hang   2. Sua so luong                   |");
            System.out.println("| 3. Xoa don hang    4. Tim don hang                   |");
            System.out.println("| 5. Thong ke        6. Xem tat ca                     |");
            System.out.println("| 0. Quay lai                                          |");
            System.out.println("+------------------------------------------------------+");
            luaChon = nhapSoNguyen("Chon chuc nang: ", 0, 6);
            try {
                if (luaChon == 1) {
                    Order order = new Order(nhapMaDonHangMoi(), nhapKhachHangDaCo());
                    int them;
                    do {
                        Product sanPham = nhapSanPhamDaCo();
                        order.themMatHang(sanPham, nhapSoDuong("So luong: "));
                        them = nhapSoNguyen("Them san pham khac? (1 Co, 0 Khong): ", 0, 1);
                    } while (them == 1);
                    store.themDonHang(order);
                    luuSauKhiThayDoi();
                    System.out.println("Da them don hang. Tong tien: " + order.tinhTongTien());
                } else if (luaChon == 2) {
                    Order order = nhapDonHangDaCo();
                    if (order.layDanhSachMatHang().isEmpty())
                        throw new IllegalArgumentException("Don hang chua co san pham.");
                    hienThiDanhSach(order.layDanhSachMatHang());
                    int dong = nhapSoNguyen("So thu tu mat hang can sua: ", 1, order.layDanhSachMatHang().size()) - 1;
                    order.layDanhSachMatHang().get(dong).ganSoLuong(nhapSoDuong("So luong moi: "));
                    luuSauKhiThayDoi();
                    System.out.println("Da cap nhat don hang. Tong tien: " + order.tinhTongTien());
                } else if (luaChon == 3) {
                    store.xoaDonHang(nhapDonHangDaCo().layMa());
                    luuSauKhiThayDoi();
                    System.out.println("Da xoa don hang.");
                } else if (luaChon == 4) {
                    hienThiDanhSach(store.timKiemDonHang(nhapChuoi("Nhap ma don hang hoac ten khach hang: ")));
                } else if (luaChon == 5) {
                    System.out.println("So don hang: " + store.layDanhSachDonHang().size() + " | Doanh thu: "
                            + store.tinhDoanhThu());
                } else if (luaChon == 6)
                    hienThiDanhSach(store.layDanhSachDonHang());
            } catch (RuntimeException e) {
                System.out.println("Khong the thuc hien thao tac don hang: " + e.getMessage());
            }
        } while (luaChon != 0);
    }

    // Hien thi menu thu chi va tinh tong cac khoan tuong ung.
    private static void menuThuChi() {
        int luaChon;
        do {
            System.out.println("\n+------------------ QUAN LY THU CHI ------------------+");
            System.out.println("| 1. Them khoan      2. Sua khoan                    |");
            System.out.println("| 3. Xoa khoan       4. Tim khoan                    |");
            System.out.println("| 5. Thong ke        6. Xem tat ca                   |");
            System.out.println("| 0. Quay lai                                        |");
            System.out.println("+-----------------------------------------------------+");
            luaChon = nhapSoNguyen("Chon chuc nang: ", 0, 6);
            try {
                if (luaChon == 1) {
                    String ma = nhapMaThuChiMoi();
                    String noiDung = nhapChuoi("Noi dung: ");
                    System.out.println("  1. Thu\n  2. Chi");
                    String loai = nhapSoNguyen("Loai: ", 1, 2) == 1 ? CashFlow.THU : CashFlow.CHI;
                    CashFlow item = new CashFlow(ma, noiDung, loai, nhapSoTien("So tien: "));
                    store.themThuChi(item);
                    luuSauKhiThayDoi();
                    System.out.println("Da them khoan thu chi.");
                } else if (luaChon == 2) {
                    CashFlow item = nhapThuChiDaCo();
                    item.ganNoiDung(nhapChuoi("Noi dung moi: "));
                    System.out.println("  1. Thu\n  2. Chi");
                    item.ganLoai(nhapSoNguyen("Loai moi: ", 1, 2) == 1 ? CashFlow.THU : CashFlow.CHI);
                    item.ganSoTien(nhapSoTien("So tien moi: "));
                    item.ganNgay(nhapNgay("Ngay (dd/MM/yyyy): "));
                    luuSauKhiThayDoi();
                    System.out.println("Da cap nhat khoan thu chi.");
                } else if (luaChon == 3) {
                    store.xoaThuChi(nhapThuChiDaCo().layMa());
                    luuSauKhiThayDoi();
                    System.out.println("Da xoa khoan thu chi.");
                } else if (luaChon == 4) {
                    hienThiDanhSach(store.timKiemThuChi(nhapChuoi("Nhap ma hoac noi dung: ")));
                } else if (luaChon == 5) {
                    double tongThu = store.tinhTongThuChi(CashFlow.THU);
                    double tongChi = store.tinhTongThuChi(CashFlow.CHI);
                    System.out.println(
                            "Tong thu: " + tongThu + " | Tong chi: " + tongChi + " | So du: " + (tongThu - tongChi));
                } else if (luaChon == 6)
                    hienThiDanhSach(store.layDanhSachThuChi());
            } catch (RuntimeException e) {
                System.out.println("Khong the thuc hien thao tac thu chi: " + e.getMessage());
            }
        } while (luaChon != 0);
    }
}
