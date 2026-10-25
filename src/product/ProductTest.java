
package product;

public class ProductTest {

    public static void main(String[] args) {

        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);

        System.out.println("ADDING PRODUCTS");

        System.out.println(service.addProduct(
                new Product(201, "Laptop", 55000, "Dell", 10)));

        System.out.println(service.addProduct(
                new Product(202, "Laptop", 65000, "HP", 8)));

        System.out.println(service.addProduct(
                new Product(203, "Headphones", 1500, "Sony", 25)));

        System.out.println(service.addProduct(
                new Product(204, "Laptop", 52000, "Dell", 5)));

        System.out.println("\nSEARCH BY COMPANY: DELL");

        for (Product p : service.getProductsByCompany("Dell")) {
            System.out.println(p.getProductName() + " - " + p.getPrice());
        }

        System.out.println("\nUPDATE PRICE OF PRODUCT 201");
        System.out.println(service.updatePrice(201, 53000));

        Product p = service.getProductById(201);
        System.out.println("Updated price: " + p.getPrice());

        System.out.println("\nUPDATE STOCK OF PRODUCT 201");
        System.out.println(service.updateQuantity(201, 15));
        System.out.println("Updated quantity: " + p.getQuantity());

        System.out.println("\nTRY DUPLICATE PRODUCT ID");
        System.out.println(service.addProduct(
                new Product(201, "Mouse", 500, "HP", 20)));

        System.out.println("\nTRY INVALID PRICE");
        System.out.println(service.updatePrice(201, -100));

        System.out.println("\nDELETE PRODUCT 203");
        System.out.println(service.deleteProduct(203));

        System.out.println("\nALL REMAINING PRODUCTS");
        for (Product item : service.getAllProducts()) {
            System.out.println(item.getProductId() + " - "
                    + item.getProductName() + " - " + item.getPrice());
        }
    }
}
