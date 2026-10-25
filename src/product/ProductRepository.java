
package product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public boolean addProduct(Product product) {
        if (product == null || findById(product.getProductId()) != null) {
            return false;
        }

        products.add(product);
        return true;
    }

    public boolean deleteProduct(int productId) {
        Product product = findById(productId);

        if (product == null) {
            return false;
        }

        products.remove(product);
        return true;
    }

    public Product findById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public List<Product> findByName(String productName) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                result.add(product);
            }
        }

        return result;
    }

    public Product findByNameAndPrice(String productName, double price) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)
                    && Double.compare(product.getPrice(), price) == 0) {
                return product;
            }
        }
        return null;
    }

    public List<Product> findByPriceRange(
            String productName, double minPrice, double maxPrice) {

        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)
                    && product.getPrice() >= minPrice
                    && product.getPrice() <= maxPrice) {
                result.add(product);
            }
        }

        return result;
    }

    public List<Product> findByCompany(String companyName) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getCompanyName().equalsIgnoreCase(companyName)) {
                result.add(product);
            }
        }

        return result;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
