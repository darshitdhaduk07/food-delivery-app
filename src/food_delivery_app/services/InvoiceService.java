package food_delivery_app.services;

import food_delivery_app.model.Invoice;
import food_delivery_app.model.Order;

public class InvoiceService {

    public Invoice generateInvoice(Order order, double finalAmount) {

        Invoice invoice = new Invoice(order, finalAmount);

        invoice.printInvoice();

        return invoice;
    }
}