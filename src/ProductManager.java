import java.util.ArrayList;
import java.util.List;

interface Searchable<T> {
    List<T> search(String keyword);
}

class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Không tìm thấy sản phẩm có mã: " + id);
    }
}

public class ProductManager implements Searchable<Product> {
    private final List<Product> products = new ArrayList<>();

    public void add(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không được rỗng");
        }

        for (Product current : products) {
            if (current.getId().equals(product.getId())) {
                throw new IllegalArgumentException(
                        "Mã sản phẩm đã tồn tại: " + product.getId());
            }
        }
        products.add(product);
    }

    public Product findById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        throw new ProductNotFoundException(id);
    }

    @Override
    public List<Product> search(String keyword) {
        return search(keyword, "");
    }

    public List<Product> search(String keyword, String category) {
        if (keyword == null || category == null) {
            throw new IllegalArgumentException(
                    "Điều kiện tìm kiếm không được null");
        }

        List<Product> results = new ArrayList<>();
        String text = keyword.trim().toLowerCase();
        String type = category.trim();

        for (Product product : products) {
            boolean matchesName =
                    product.getName().toLowerCase().contains(text);
            boolean matchesCategory =
                    type.isEmpty()
                    || product.category().equalsIgnoreCase(type);

            if (matchesName && matchesCategory) {
                results.add(product);
            }
        }
        return results;
    }

    public void deactivate(String id) {
        Product product = findById(id);
        product.setActive(false);
    }

    public List<Product> listAll() {
        return new ArrayList<>(products);
    }

    public long countActive() {
        long count = 0;
        for (Product product : products) {
            if (product.isActive()) {
                count++;
            }
        }
        return count;
    }
}
