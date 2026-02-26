package food_delivery_app.model;

import java.time.LocalDateTime;

public class Invoice {

    private static int counter = 0;

    private int invoiceId;
    private Order order;
    private double grand;
    private LocalDateTime time;
    private double discount;

    public Invoice(Order order, double grandTotal,double discount) {
        this.invoiceId = ++counter;
        this.order = order;
        this.grand = grandTotal;
        this.discount = discount;
        this.time = LocalDateTime.now();
    }

    public void printInvoice() {

        System.out.println("\n=================== INVOICE ===================");
        System.out.println("Invoice ID : " + invoiceId);
        System.out.println("Order ID   : " + order.getId());
        System.out.println("Customer   : " +
                order.getCustomer().getName());
        System.out.println("Time       : " + time);

        System.out.println("-----------------------------------------");
        System.out.printf("%-20s %-10s %-10s%n",
                "Item Name", "Qty", "Price");
        System.out.println("-----------------------------------------");

        for (OrderItem item : order.getItems()) {

            System.out.printf("%-20s %-10d ₹%-10.2f%n",
                    item.getItemName(),
                    item.getQuantity(),
                    item.getPrice());
        }

        System.out.println("-----------------------------------------");



        System.out.printf("%-20s %-10s ₹%-10.2f%n",
                "SUBTOTAL",
                "",
                grand);

        if (discount > 0) {
            System.out.printf("%-20s %-10s ₹%-10.2f%n",
                    "DISCOUNT",
                    "",
                    discount);
        }

        System.out.printf("%-20s %-10s ₹%-10.2f%n",
                "TOTAL",
                "",
                grand-discount);

        System.out.println("=========================================\n");
    }
}