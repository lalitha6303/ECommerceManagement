
package product;

import java.util.List;

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }

        if (product.getProductName() == null
                || product.getProductName().isBlank()
                || product.getCompanyName() == null
                || product.getCompanyName().isBlank()
                || product.getPrice() <= 0
                || product.getQuantity() < 0) {
            return false;
        }

        if (repository.findById(product.getProductId()) != null) {
            return false;
        }

        return repository.addProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return repository.deleteProduct(productId);
    }

    public Product getProductById(int productId) {
        return repository.findById(productId);
    }

    public List<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    public Product getProductByNameAndPrice(String name, double price) {
        return repository.findByNameAndPrice(name, price);
    }

    public List<Product> getProductsByPriceRange(
            String name, double minPrice, double maxPrice) {

        if (minPrice < 0 || maxPrice < minPrice) {
            return List.of();
        }

        return repository.findByPriceRange(name, minPrice, maxPrice);
    }

    public List<Product> getProductsByCompany(String companyName) {
        return repository.findByCompany(companyName);
    }

    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    public boolean updatePrice(int productId, double newPrice) {
        Product product = repository.findById(productId);

        if (product == null || newPrice <= 0) {
            return false;
        }

        product.setPrice(newPrice);
        return true;
    }

    public boolean updateQuantity(int productId, int newQuantity) {
        Product product = repository.findById(productId);

        if (product == null || newQuantity < 0) {
            return false;
        }

        product.setQuantity(newQuantity);
        return true;
    }
}
