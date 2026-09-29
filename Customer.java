import java.util.Objects;

public class Customer {
    private String id;
    private String name;
    private String phone;

    public Customer(String id, String name) {
        this(id, name, "Chưa có");
    }

    public Customer(String id, String name, String phone) {
        setId(id);
        setName(name);
        setPhone(phone);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty() || id.contains(";"))
            throw new IllegalArgumentException("Mã khách hàng không hợp lệ");
        this.id = id.trim();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty() || name.contains(";"))
            throw new IllegalArgumentException("Tên khách hàng không hợp lệ");
        this.name = name.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty() || phone.contains(";"))
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        this.phone = phone.trim();
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + phone;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Customer))
            return false;
        return id.equalsIgnoreCase(((Customer) object).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }
}
