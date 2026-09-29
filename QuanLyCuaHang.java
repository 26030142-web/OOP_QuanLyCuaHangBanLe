import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuanLyCuaHang implements TimKiem<Product>, LuuDuLieu {
    private static final QuanLyCuaHang INSTANCE = new QuanLyCuaHang();
    private static final String FOLDER = "data";
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<ThuChi> thuChiList = new ArrayList<ThuChi>();

    private QuanLyCuaHang() {
    }

    public static QuanLyCuaHang getInstance() {
        return INSTANCE;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<ThuChi> getThuChiList() {
        return thuChiList;
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class DataException extends RuntimeException {
        public DataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public Product findProduct(String id) {
        for (Product p : products)
            if (p.getId().equalsIgnoreCase(id))
                return p;
        throw new ProductNotFoundException("Không tìm thấy sản phẩm: " + id);
    }

    public Customer findCustomer(String id) {
        for (Customer c : customers)
            if (c.getId().equalsIgnoreCase(id))
                return c;
        throw new IllegalArgumentException("Không tìm thấy khách hàng: " + id);
    }

    public Order findOrder(String id) {
        for (Order o : orders)
            if (o.getId().equalsIgnoreCase(id))
                return o;
        throw new IllegalArgumentException("Không tìm thấy đơn hàng: " + id);
    }

    public ThuChi findThuChi(String id) {
        for (ThuChi t : thuChiList)
            if (t.getId().equalsIgnoreCase(id))
                return t;
        throw new IllegalArgumentException("Không tìm thấy khoản thu chi: " + id);
    }

    public void addProduct(Product p) {
        for (Product old : products)
            if (old.equals(p))
                throw new IllegalArgumentException("Mã sản phẩm đã tồn tại");
        products.add(p);
    }

    public void deleteProduct(String id) {
        products.remove(findProduct(id));
    }

    // Hai cách tìm: theo mã/tên hoặc giới hạn trong một loại sản phẩm.
    @Override
    public ArrayList<Product> timKiem(String keyword) {
        return timKiem(keyword, "");
    }

    public ArrayList<Product> timKiem(String keyword, String category) {
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product p : products) {
            boolean matches = p.getId().toLowerCase().contains(keyword.toLowerCase())
                    || p.getName().toLowerCase().contains(keyword.toLowerCase());
            boolean typeMatches = category.isEmpty() || p.getCategory().equalsIgnoreCase(category);
            if (matches && typeMatches)
                result.add(p);
        }
        return result;
    }

    public void addCustomer(Customer c) {
        for (Customer old : customers)
            if (old.equals(c))
                throw new IllegalArgumentException("Mã khách đã tồn tại");
        customers.add(c);
    }

    public void deleteCustomer(String id) {
        Customer customer = findCustomer(id);
        for (Order order : orders) {
            if (order.getCustomer().equals(customer)) {
                throw new IllegalArgumentException("Khách đã có đơn hàng, không thể xóa");
            }
        }
        customers.remove(customer);
    }

    public ArrayList<Customer> searchCustomers(String key) {
        ArrayList<Customer> result = new ArrayList<Customer>();
        for (Customer c : customers)
            if (c.getId().toLowerCase().contains(key.toLowerCase())
                    || c.getName().toLowerCase().contains(key.toLowerCase()))
                result.add(c);
        return result;
    }

    public void addOrder(Order order) {
        for (Order old : orders)
            if (old.equals(order))
                throw new IllegalArgumentException("Mã đơn đã tồn tại");
        orders.add(order);
    }

    public void deleteOrder(String id) {
        orders.remove(findOrder(id));
    }

    public ArrayList<Order> searchOrders(String key) {
        ArrayList<Order> result = new ArrayList<Order>();
        for (Order o : orders)
            if (o.getId().toLowerCase().contains(key.toLowerCase())
                    || o.getCustomer().getName().toLowerCase().contains(key.toLowerCase()))
                result.add(o);
        return result;
    }

    public void addThuChi(ThuChi item) {
        for (ThuChi old : thuChiList)
            if (old.equals(item))
                throw new IllegalArgumentException("Mã thu chi đã tồn tại");
        thuChiList.add(item);
    }

    public void deleteThuChi(String id) {
        thuChiList.remove(findThuChi(id));
    }

    public ArrayList<ThuChi> searchThuChi(String key) {
        ArrayList<ThuChi> result = new ArrayList<ThuChi>();
        for (ThuChi t : thuChiList)
            if (t.getId().toLowerCase().contains(key.toLowerCase())
                    || t.getContent().toLowerCase().contains(key.toLowerCase()))
                result.add(t);
        return result;
    }

    public double totalRevenue() {
        double total = 0;
        for (Order o : orders)
            total += o.getTotal();
        return total;
    }

    public double totalByType(String type) {
        double total = 0;
        for (ThuChi t : thuChiList)
            if (t.getType().equals(type))
                total += t.getAmount();
        return total;
    }

    private String path(String file) {
        return FOLDER + "/" + file;
    }

    private void write(String file, List<String> lines) throws IOException {
        Files.createDirectories(Paths.get(FOLDER));
        Files.write(Paths.get(path(file)), lines, StandardCharsets.UTF_8);
    }

    private List<String> read(String file) throws IOException {
        if (!Files.exists(Paths.get(path(file))))
            return new ArrayList<String>();
        return Files.readAllLines(Paths.get(path(file)), StandardCharsets.UTF_8);
    }

    @Override
    public void ghiFile() {
        try {
            ArrayList<String> rows = new ArrayList<String>();
            for (Product p : products)
                rows.add(p.getId() + ";" + p.getName() + ";" + p.getPrice() + ";" + p.getStock() + ";" + p.getCategory()
                        + ";" + rawInfo(p));
            write("products.csv", rows);
            rows = new ArrayList<String>();
            for (Customer c : customers)
                rows.add(c.getId() + ";" + c.getName() + ";" + c.getPhone());
            write("customers.csv", rows);
            rows = new ArrayList<String>();
            for (Order o : orders)
                rows.add(o.getId() + ";" + o.getCustomer().getId());
            write("orders.csv", rows);
            rows = new ArrayList<String>();
            for (Order o : orders)
                for (OrderItem i : o.getItems())
                    rows.add(o.getId() + ";" + i.getProductName() + ";" + i.getUnitPrice() + ";" + i.getQuantity());
            write("order_items.csv", rows);
            rows = new ArrayList<String>();
            for (ThuChi t : thuChiList)
                rows.add(
                        t.getId() + ";" + t.getContent() + ";" + t.getType() + ";" + t.getAmount() + ";" + t.getDate());
            write("thuchi.csv", rows);
        } catch (IOException e) {
            throw new DataException("Không ghi được dữ liệu ra file", e);
        }
    }

    private String rawInfo(Product p) {
        if (p instanceof Food)
            return ((Food) p).getExpiryDate();
        if (p instanceof Electronics)
            return String.valueOf(((Electronics) p).getWarrantyMonths());
        return ((Clothing) p).getSize();
    }

    @Override
    public void docFile() {
        try {
            products.clear();
            customers.clear();
            orders.clear();
            thuChiList.clear();
            for (String row : read("products.csv")) {
                String[] a = row.split(";", -1);
                addProduct(ProductFactory.create(a[4], a[0], a[1], Double.parseDouble(a[2]), Integer.parseInt(a[3]),
                        a[5]));
            }
            for (String row : read("customers.csv")) {
                String[] a = row.split(";", -1);
                addCustomer(new Customer(a[0], a[1], a[2]));
            }
            for (String row : read("orders.csv")) {
                String[] a = row.split(";", -1);
                addOrder(new Order(a[0], findCustomer(a[1])));
            }
            for (String row : read("order_items.csv")) {
                String[] a = row.split(";", -1);
                findOrder(a[0]).addItem(new OrderItem(a[1], Double.parseDouble(a[2]), Integer.parseInt(a[3])));
            }
            for (String row : read("thuchi.csv")) {
                String[] a = row.split(";", -1);
                addThuChi(new ThuChi(a[0], a[1], a[2], Double.parseDouble(a[3]), a[4]));
            }
        } catch (IOException | RuntimeException e) {
            throw new DataException("Không đọc được dữ liệu. Kiểm tra các file trong thư mục data", e);
        }
    }
}
