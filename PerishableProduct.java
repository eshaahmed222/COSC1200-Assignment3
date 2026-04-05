/**
 * @author : Esha Minhaj
 * Date: 4-April- 2026
 * Description: This class stores information for a perishable product.
 */
import java.time.LocalDate;

public class PerishableProduct extends Product {
    private LocalDate expiryDate;

    // Default constructor
    public PerishableProduct() {
        super();
        this.expiryDate = LocalDate.now();
    }

    // Parameterized constructor
    public PerishableProduct(String sku, String productName, double unitCost, double salePrice,
                             int quantityOnHand, int quantityNeeded, String specialInstructions,
                             LocalDate expiryDate) {
        super(sku, productName, unitCost, salePrice, quantityOnHand, quantityNeeded, specialInstructions);
        setExpiryDate(expiryDate);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if (expiryDate != null) {
            this.expiryDate = expiryDate;
        } else {
            throw new IllegalArgumentException("Expiry date cannot be blank.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nExpiry Date: " + expiryDate;
    }
}