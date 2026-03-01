package food_delivery_app.service;

import food_delivery_app.model.Invoice;
import food_delivery_app.model.order.Order;

public class InvoiceService {

    public Invoice generateInvoice(Order order, double finalAmount,double discount) {

        Invoice invoice = new Invoice(order, finalAmount,discount);

        invoice.printInvoice();

        return invoice;
    }
}