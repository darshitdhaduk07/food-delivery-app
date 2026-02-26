package food_delivery_app.services;

import food_delivery_app.cart.Cart;
import food_delivery_app.discount.DiscountStrategy;
import food_delivery_app.model.*;
import food_delivery_app.payment.PaymentMethod;
import food_delivery_app.payment.PaymentProcessor;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;
import food_delivery_app.utility.InputValidator;

import java.util.List;
import java.util.Map;

public class OrderService {

    private final OrderRepository orderRepo;
    private final DeliveryRepository deliveryRepository;
    private final CartService cartService;
    private final DeliveryService deliveryService;
    private final InvoiceService invoiceService = new InvoiceService();

    public OrderService() {
        this.orderRepo = OrderRepository.getInstance();
        this.cartService = new CartService();
        this.deliveryService = new DeliveryService();
        this.deliveryRepository = DeliveryRepository.getInstance();
    }
    public boolean processPayment(double amount)
    {
        int i = 1;
        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.print(i+".");
            System.out.println(method);
            i++;
        }
        int choice = InputValidator.readInt("Enter your choice for payment: ",1,PaymentMethod.values().length);
        PaymentProcessor processor = new PaymentProcessor();
        return processor.process(PaymentMethod.values()[choice-1],amount);


    }

    //place order
    public Order placeOrder(Customer customer, Address deliveryAddress, DiscountStrategy discountStrategy) throws InterruptedException {

        Cart cart = customer.getCart();

        //validate cart
        if (cartService.isCartEmpty(cart)) {
            System.out.println("Cart is empty.");
            return null;
        }

        //take order item
        List<OrderItem> orderItems = cartService.convertToOrderItems(cart);

        //create order
        Order order = new Order(customer, orderItems, deliveryAddress);
        order.moveToNextState();

        //apply discount
        double finalAmount = order.getTotalAmount();
        double discount  = 0;

        if (discountStrategy != null) {
            discount = discountStrategy.applyDiscount(finalAmount);
        }

        //payment
        boolean paymentSuccess = processPayment(finalAmount-discount);
        System.out.println("\nPayment Processing ...\n");
        Thread.sleep(2000);

        if (!paymentSuccess) {
            System.out.println("\nPayment failed.\n");
            return null;
        }
        else {
            System.out.println("Success!\n");
        }


        //confirm order
        order.moveToNextState();
        System.out.println("Order placed successfully.\n");

        //save order
        orderRepo.addOrderToHistory(order);

        DeliveryAgent agent = deliveryService.assignDeliveryAgent(order);
        if(agent != null)
        {
            order.setDeliveryAgent(agent);
        }
        else
            orderRepo.addOrder(order);

        //clear cart
        cartService.clearCart(cart);



        //print invoice
        System.out.println("\nInvoice generating... \n");
        Thread.sleep(2000);
        invoiceService.generateInvoice(order, finalAmount,discount);

        return order;
    }

    public Order getOrder(int orderId) {
        return orderRepo.findById(orderId);
    }
}