import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item {
    String name;
    int quantity;
    double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    public double getTotal() {
        return quantity * price;
    }
}

public class InvoiceGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = new ArrayList<>();
        
        System.out.println("Invoice Generator");
        System.out.println("Enter customer name: ");
        String customerName = scanner.nextLine();

        while (true) {
            System.out.print("Enter item name (or 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            items.add(new Item(name, quantity, price));
        }

        generateInvoice(customerName, items);
        System.out.println("Invoice generated successfully!");
    }
    
    public static void generateInvoice(String customerName, List<Item> items) {
        double totalAmount = 0;
        StringBuilder invoice = new StringBuilder();
        invoice.append("INVOICE\n");
        invoice.append("Customer: ").append(customerName).append("\n");
        invoice.append("--------------------------------------\n");
        invoice.append(String.format("%-20s %-10s %-10s %-10s\n", "Item", "Qty", "Price", "Total"));
        invoice.append("--------------------------------------\n");
        
        for (Item item : items) {
            invoice.append(String.format("%-20s %-10d %-10.2f %-10.2f\n", 
                item.name, item.quantity, item.price, item.getTotal()));
            totalAmount += item.getTotal();
        }
        
        invoice.append("--------------------------------------\n");
        invoice.append(String.format("%-30s %-10.2f\n", "Total Amount:", totalAmount));
        
        try (FileWriter writer = new FileWriter("invoice.txt")) {
            writer.write(invoice.toString());
        } catch (IOException e) {
            System.out.println("Error saving invoice: " + e.getMessage());
        }
    }
}
