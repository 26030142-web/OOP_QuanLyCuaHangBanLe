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
        if (keyword == null) {
            throw new IllegalArgumentException("Từ khóa không được rỗng");
        }

        List<Product> results = new ArrayList<>();
        String text = keyword.trim().toLowerCase();

        for (Product product : products) {
            if (product.getName().toLowerCase().contains(text)) {
                results.add(product);
            }
        }
        return results;
    }
}
