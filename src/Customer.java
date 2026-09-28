import java.util.Objects;

public class Customer {
    private final String id;
    private String name;
    private String phone;
    private boolean active;

    public Customer(String id, String name) {
        this(id, name, "Chưa có");
    }

    public Customer(String id, String name, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã khách hàng không được rỗng");
        }

        this.id = id.trim();
        setName(name);
        setPhone(phone);
        this.active = true;
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

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên khách hàng không được rỗng");
        }
        this.name = name.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Số điện thoại không được rỗng");
        }
        this.phone = phone.trim();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + phone;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
