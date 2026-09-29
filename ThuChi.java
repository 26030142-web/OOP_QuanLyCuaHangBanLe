import java.util.Objects;
import java.time.LocalDate;

public class ThuChi {
    public static final String THU = "THU";
    public static final String CHI = "CHI";
    private String id;
    private String content;
    private String type;
    private double amount;
    private String date;

    public ThuChi(String id, String content, String type, double amount) {
        this(id, content, type, amount, LocalDate.now().toString());
    }

    public ThuChi(String id, String content, String type, double amount, String date) {
        setId(id);
        setContent(content);
        setType(type);
        setAmount(amount);
        setDate(date);
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = checkText(id, "Mã thu chi");
    }

    public void setContent(String content) {
        this.content = checkText(content, "Nội dung");
    }

    public void setType(String type) {
        if (!THU.equalsIgnoreCase(type) && !CHI.equalsIgnoreCase(type))
            throw new IllegalArgumentException("Loại phải là THU hoặc CHI");
        this.type = type.toUpperCase();
    }

    public void setAmount(double amount) {
        if (amount <= 0 || Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Số tiền phải là số lớn hơn 0");
        this.amount = amount;
    }

    public void setDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ngày phải có dạng yyyy-MM-dd");
        }
        this.date = date;
    }

    private String checkText(String text, String label) {
        if (text == null || text.trim().isEmpty() || text.contains(";"))
            throw new IllegalArgumentException(label + " không hợp lệ");
        return text.trim();
    }

    @Override
    public String toString() {
        return id + " | " + type + " | " + content + " | " + amount + " | " + date;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof ThuChi))
            return false;
        return id.equalsIgnoreCase(((ThuChi) object).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }
}
