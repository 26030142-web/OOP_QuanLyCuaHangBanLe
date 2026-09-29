import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private String id;
    private Customer customer;
    private ArrayList<OrderItem> items;

    public Order(String id, Customer customer) {
        this(id, customer, new ArrayList<OrderItem>());
    }

    public Order(String id, Customer customer, ArrayList<OrderItem> items) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("Mã đơn không hợp lệ");
        if (customer == null || items == null)
            throw new IllegalArgumentException("Đơn hàng thiếu dữ liệu");
        this.id = id.trim();
        this.customer = customer;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }

    public void addItem(OrderItem item) {
        if (item == null)
            throw new IllegalArgumentException("Dòng hàng không được rỗng");
        items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items)
            total += item.getAmount();
        return total;
    }

    @Override
    public String toString() {
        return id + " | Khách: " + customer.getName() + " | Tổng tiền: " + getTotal();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Order))
            return false;
        return id.equalsIgnoreCase(((Order) object).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }
}
