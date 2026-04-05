// minor update
/**
 * @author : Esha Minhaj
 * Date:4 April 2026
 * Description: This program allows the user to create, edit, delete,
 * and display products in a small retail store system.
 */
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== Product Menu =====");
            System.out.println("1) Create Product");
            System.out.println("2) Create Perishable Product");
            System.out.println("3) Edit Product by SKU");
            System.out.println("4) Delete Product by SKU");
            System.out.println("5) Display Product by SKU");
            System.out.println("6) Display all Products");
            System.out.println("7) Exit");

            choice = readMenuChoice();

            switch (choice) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    createPerishableProduct();
                    break;
                case 3:
                    editProductBySku();
                    break;
                case 4:
                    deleteProductBySku();
                    break;
                case 5:
                    displayProductBySku();
                    break;
                case 6:
                    displayAllProducts();
                    break;
                case 7:
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);
    }

    public static int readMenuChoice() {
        int choice;

        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static void createProduct() {
        try {
            System.out.println("\n--- Create Product ---");

            String sku = readUniqueSku();
            String productName = readNonBlankString("Enter product name: ");
            double unitCost = readNonNegativeDouble("Enter unit cost: ");
            double salePrice = readNonNegativeDouble("Enter sale price: ");
            int quantityOnHand = readNonNegativeInt("Enter quantity on hand: ");
            int quantityNeeded = readNonNegativeInt("Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String instructions = scanner.nextLine();

            Product product = new Product(sku, productName, unitCost, salePrice,
                    quantityOnHand, quantityNeeded, instructions);

            productList.add(product);
            System.out.println("Product created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createPerishableProduct() {
        try {
            System.out.println("\n--- Create Perishable Product ---");

            String sku = readUniqueSku();
            String productName = readNonBlankString("Enter product name: ");
            double unitCost = readNonNegativeDouble("Enter unit cost: ");
            double salePrice = readNonNegativeDouble("Enter sale price: ");
            int quantityOnHand = readNonNegativeInt("Enter quantity on hand: ");
            int quantityNeeded = readNonNegativeInt("Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String instructions = scanner.nextLine();

            LocalDate expiryDate = readDate("Enter expiry date (YYYY-MM-DD): ");

            PerishableProduct product = new PerishableProduct(
                    sku, productName, unitCost, salePrice,
                    quantityOnHand, quantityNeeded, instructions, expiryDate
            );

            productList.add(product);
            System.out.println("Perishable product created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void editProductBySku() {
        System.out.println("\n--- Edit Product by SKU ---");
        String sku = readSku("Enter SKU to edit: ");

        Product product = findProductBySku(sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
            return;
        }

        try {
            String productName = readNonBlankString("Enter new product name: ");
            double unitCost = readNonNegativeDouble("Enter new unit cost: ");
            double salePrice = readNonNegativeDouble("Enter new sale price: ");
            int quantityOnHand = readNonNegativeInt("Enter new quantity on hand: ");
            int quantityNeeded = readNonNegativeInt("Enter new quantity needed: ");

            System.out.print("Enter new special instructions: ");
            String instructions = scanner.nextLine();

            product.setProductName(productName);
            product.setUnitCost(unitCost);
            product.setSalePrice(salePrice);
            product.setQuantityOnHand(quantityOnHand);
            product.setQuantityNeeded(quantityNeeded);
            product.setSpecialInstructions(instructions);

            if (product instanceof PerishableProduct) {
                LocalDate expiryDate = readDate("Enter new expiry date (YYYY-MM-DD): ");
                ((PerishableProduct) product).setExpiryDate(expiryDate);
            }

            System.out.println("Product updated successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteProductBySku() {
        System.out.println("\n--- Delete Product by SKU ---");
        String sku = readSku("Enter SKU to delete: ");

        Product product = findProductBySku(sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
        } else {
            productList.remove(product);
            System.out.println("Product deleted successfully.");
        }
    }

    public static void displayProductBySku() {
        System.out.println("\n--- Display Product by SKU ---");
        String sku = readSku("Enter SKU to display: ");

        Product product = findProductBySku(sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
        } else {
            System.out.println("\n" + product);
        }
    }

    public static void displayAllProducts() {
        System.out.println("\n--- All Products ---");

        if (productList.isEmpty()) {
            System.out.println("There are no products in the system.");
            return;
        }

        for (Product product : productList) {
            System.out.println(product);
            System.out.println("----------------------------");
        }
    }

    public static Product findProductBySku(String sku) {
        for (Product product : productList) {
            if (product.getSku().equals(sku)) {
                return product;
            }
        }
        return null;
    }

    public static String readUniqueSku() {
        String sku;

        while (true) {
            sku = readSku("Enter SKU (8 or more digits): ");

            if (findProductBySku(sku) == null) {
                return sku;
            } else {
                System.out.println("That SKU already exists.");
            }
        }
    }

    public static String readSku(String message) {
        String sku;

        while (true) {
            System.out.print(message);
            sku = scanner.nextLine().trim();

            if (sku.matches("\\d{8,}")) {
                return sku;
            } else {
                System.out.println("SKU must be 8 or more digits.");
            }
        }
    }

    public static String readNonBlankString(String message) {
        String value;

        while (true) {
            System.out.print(message);
            value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            } else {
                System.out.println("This field cannot be blank.");
            }
        }
    }

    public static double readNonNegativeDouble(String message) {
        double value;

        while (true) {
            System.out.print(message);

            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine();

                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter 0 or greater.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static int readNonNegativeInt(String message) {
        int value;

        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();

                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter 0 or greater.");
                }
            } else {
                System.out.println("Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    public static LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
}
