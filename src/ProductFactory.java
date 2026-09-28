import java.math.BigDecimal;

public class ProductFactory {

    public static Product create(String type, String id, String name,
                                 BigDecimal price, int stock, String info) {
        if (type == null) {
            throw new IllegalArgumentException("Loại sản phẩm không được rỗng");
        }

        switch (type.trim().toLowerCase()) {
            case "food":
                return new Food(id, name, price, stock, info);

            case "electronics":
                int warrantyMonths;
                try {
                    warrantyMonths = Integer.parseInt(info);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Số tháng bảo hành phải là số nguyên", e);
                }
                return new Electronics(
                        id, name, price, stock, warrantyMonths);

            case "clothing":
                return new Clothing(id, name, price, stock, info);

            default:
                throw new IllegalArgumentException(
                        "Loại sản phẩm không hợp lệ: " + type);
        }
    }
}
