public class ProductFactory {
    public static Product create(String type, String id, String name, double price, int stock, String info) {
        if (type == null)
            throw new IllegalArgumentException("Chưa chọn loại sản phẩm");
        switch (type.toLowerCase()) {
            case "food":
                return new Food(id, name, price, stock, info);
            case "electronics":
                return new Electronics(id, name, price, stock, Integer.parseInt(info));
            case "clothing":
                return new Clothing(id, name, price, stock, info);
            default:
                throw new IllegalArgumentException("Loại sản phẩm không hợp lệ");
        }
    }
}
