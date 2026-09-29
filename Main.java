import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final QuanLyCuaHang shop = QuanLyCuaHang.getInstance();

    public static void main(String[] args) {
        try {
            shop.docFile();
        } catch (QuanLyCuaHang.DataException e) {
            System.out.println(e.getMessage());
        }
        int choice;
        do {
            System.out.println("\n===== QUAN LY CUA HANG BAN  =====");
            System.out
                    .println("1. Quan ly san pham\n2. Quan ly khach hang\n3. Quan Ly Don hang \n4. Thu chi\n0. Thoat");
            choice = readInt("Chon: ", 0, 4);
            try {
                switch (choice) {
                    case 1:
                        productMenu();
                        break;
                    case 2:
                        customerMenu();
                        break;
                    case 3:
                        orderMenu();
                        break;
                    case 4:
                        thuChiMenu();
                        break;
                    case 0:
                        shop.ghiFile();
                        System.out.println("Da luu du lieu va thoat.");
                        break;
                    default:
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Khong the thuc hien: " + e.getMessage());
            }
        } while (choice != 0);
        input.close();
    }

    private static String readText(String label) {
        while (true) {
            System.out.print(label);
            String value = input.nextLine().trim();
            if (!value.isEmpty())
                return value;
            System.out.println("Khong duoc de trong.");
        }
    }

    private static int readInt(String label, int min, int max) {
        while (true) {
            System.out.print(label);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value >= min && value <= max)
                    return value;
            } catch (NumberFormatException e) {
                System.out.println("Hay nhap so hop le.");
            }
            System.out.println("Nhap so tu " + min + " den " + max + ".");
        }
    }

    private static int readPositiveInt(String label) {
        return readInt(label, 1, Integer.MAX_VALUE);
    }

    private static double readPositiveDouble(String label) {
        while (true) {
            System.out.print(label);
            try {
                double value = Double.parseDouble(input.nextLine().trim());
                if (value > 0)
                    return value;
            } catch (NumberFormatException e) {
                System.out.println("Hay nhap so hop le.");
            }
            System.out.println("Gia tri phai lon hon 0.");
        }
    }

    private static void showList(Iterable<?> list) {
        boolean empty = true;
        for (Object item : list) {
            System.out.println(item);
            empty = false;
        }
        if (empty)
            System.out.println("Danh sach dang trong!!.");
    }

    private static void saved() {
        shop.ghiFile();
    }

    private static Product readProduct(String id, int stock) {
        return readProduct(id, stock, null);
    }

    private static Product readProduct(String id, int stock, String currentCategory) {
        String name = readText("Ten San Pham: ");
        double price = readPositiveDouble("Giá bán: ");
        if (currentCategory == null)
            System.out.println("1. Food\n2. Electronics\n3. Clothing");
        else
            stock = readInt("Ton Kho Moi: ", 0, Integer.MAX_VALUE);
        String typeName;
        String info;
        int type = currentCategory == null ? readInt("Loai: ", 1, 3)
                : currentCategory.equals("Food") ? 1
                        : currentCategory.equals("Electronics") ? 2 : 3;
        if (type == 1) {
            typeName = "food";
            info = readText("HSD (yyyy-MM-dd): ");
        } else if (type == 2) {
            typeName = "electronics";
            info = String.valueOf(readInt("Bao Hanh (Thang): ", 0, 100));
        } else {
            typeName = "clothing";
            info = readText("Kich co: ");
        }
        return ProductFactory.create(typeName, id, name, price, stock, info);
    }

    private static void productMenu() {
        int choice;
        do {
            System.out.println(
                    "\n--- SaN PHAM ---\n1. Them\n2. Sua\n3. Xoa\n4. Tim kiem\n5. Thong ke\n6. Danh sach\n0. Quay lai");
            choice = readInt("Chon: ", 0, 6);
            try {
                if (choice == 1) {
                    Product p = readProduct(readText("Ma san pham: "), 0);
                    shop.addProduct(p);
                    saved();
                    System.out.println("Da them san pham.");
                } else if (choice == 2) {
                    Product old = shop.findProduct(readText("Ma can sua: "));
                    Product fresh = readProduct(old.getId(), old.getStock(), old.getCategory());
                    old.setName(fresh.getName());
                    old.setPrice(fresh.getPrice());
                    old.setStock(fresh.getStock());
                    if (old instanceof Food)
                        ((Food) old).setExpiryDate(((Food) fresh).getExpiryDate());
                    if (old instanceof Electronics)
                        ((Electronics) old).setWarrantyMonths(((Electronics) fresh).getWarrantyMonths());
                    if (old instanceof Clothing)
                        ((Clothing) old).setSize(((Clothing) fresh).getSize());
                    saved();
                    System.out.println("Da sua.");
                } else if (choice == 3) {
                    shop.deleteProduct(readText("Ma can xoa: "));
                    saved();
                    System.out.println("Da xoa.");
                } else if (choice == 4) {
                    String key = readText("Nhap ma hoac ten: ");
                    showList(shop.timKiem(key));
                    System.out.println("Ket qua " + shop.coKetQua(key));
                } else if (choice == 5) {
                    int food = 0, electronics = 0, clothing = 0, stock = 0;
                    for (Product p : shop.getProducts()) {
                        if (p instanceof Food)
                            food++;
                        else if (p instanceof Electronics)
                            electronics++;
                        else if (p instanceof Clothing)
                            clothing++;
                        stock += p.getStock();
                    }
                    System.out.println("Food: " + food + " | Electronics: " + electronics + " | Clothing: " + clothing);
                    System.out.println("Tong san Pham: " + shop.getProducts().size() + " | Tong ton kho: " + stock);
                } else if (choice == 6)
                    showList(shop.getProducts());
            } catch (RuntimeException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void customerMenu() {
        int choice;
        do {
            System.out.println(
                    "\n--- Quan ly khach hang ---\n1. Them\n2. Sua\n3. Xoa\n4. Tim kiem\n5. Thong ke\n6. Danh sach\n0. Quay lai");
            choice = readInt("Chon: ", 0, 6);
            try {
                if (choice == 1) {
                    shop.addCustomer(
                            new Customer(readText("Ma Khac: "), readText("Ten: "), readText("So dien thoai: ")));
                    saved();
                    System.out.println("Da them khah hang.");
                } else if (choice == 2) {
                    Customer c = shop.findCustomer(readText("Ma khach hang can sua: "));
                    c.setName(readText("Ten moi "));
                    c.setPhone(readText("So dien thoai moi: "));
                    saved();
                    System.out.println("Da sua.");
                } else if (choice == 3) {
                    shop.deleteCustomer(readText("Ma khach hang can xoa: "));
                    saved();
                    System.out.println("da xoa!.");
                } else if (choice == 4)
                    showList(shop.searchCustomers(readText("Ma hoac ten: ")));
                else if (choice == 5)
                    System.out.println("Tong khach hang: " + shop.getCustomers().size());
                else if (choice == 6)
                    showList(shop.getCustomers());
            } catch (RuntimeException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void orderMenu() {
        int choice;
        do {
            System.out.println(
                    "\n--- Quan Ly don hang ---\n1. Them\n2. Sua\n3. Xoa\n4. Tim kiem\n5. Thong ke\n6. Danh sach\n0. Quay lai");
            choice = readInt("Chon: ", 0, 6);
            try {
                if (choice == 1) {
                    String id = readText("ma don: ");
                    Customer c = shop.findCustomer(readText("ma khach: "));
                    Order order = new Order(id, c);
                    int more;
                    do {
                        Product p = shop.findProduct(readText("Ma san pham: "));
                        order.addItem(p, readPositiveInt("So Luong: "));
                        more = readInt("Them dong hang (1 Co, 0 Khong): ", 0, 1);
                    } while (more == 1);
                    shop.addOrder(order);
                    saved();
                    System.out.println("Da tao don. Tong tien: " + order.getTotal());
                } else if (choice == 2) {
                    Order order = shop.findOrder(readText("Ma don: "));
                    if (order.getItems().isEmpty())
                        throw new IllegalArgumentException("Don Chua co san pham");
                    showList(order.getItems());
                    int line = readInt("Sso Thu tu dong can sua: ", 1, order.getItems().size()) - 1;
                    order.getItems().get(line).setQuantity(readPositiveInt("So luong moi: "));
                    saved();
                    System.out.println("Da tao don. Tong tien:  " + order.getTotal());
                } else if (choice == 3) {
                    shop.deleteOrder(readText("Ma don can xoa: "));
                    saved();
                    System.out.println("Da xoa.");
                } else if (choice == 4)
                    showList(shop.searchOrders(readText("Ma don hoac ten khach hang: ")));
                else if (choice == 5)
                    System.out.println("Tong don " + shop.getOrders().size() + " | Doanh thu: " + shop.totalRevenue());
                else if (choice == 6)
                    showList(shop.getOrders());
            } catch (RuntimeException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void thuChiMenu() {
        int choice;
        do {
            System.out.println(
                    "\n--- THU CHI ---\n1. Them\n2. Tim kiem\n3. Thong ke\n4. Danh sach\n0. Quay lai");
            choice = readInt("Chon: ", 0, 6);
            try {
                if (choice == 1) {
                    String id = readText("Ma: ");
                    String content = readText("Noi dung: ");
                    System.out.println("1. Thu\n2. Chi");
                    String type = readInt("Loai: ", 1, 2) == 1 ? ThuChi.THU : ThuChi.CHI;
                    shop.addThuChi(new ThuChi(id, content, type, readPositiveDouble("So tien: ")));
                    saved();
                    System.out.println("Da them Khoan thu chi.");
                } else if (choice == 2)
                    showList(shop.searchThuChi(readText("Ma hoac noi dung ")));
                else if (choice == 3) {
                    double thu = shop.totalByType(ThuChi.THU), chi = shop.totalByType(ThuChi.CHI);
                    System.out.println("Tong thu: " + thu + " |Tong chi: " + chi + " | Con lai: " + (thu - chi));
                } else if (choice == 4)
                    showList(shop.getThuChiList());
            } catch (RuntimeException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        } while (choice != 0);
    }
}
