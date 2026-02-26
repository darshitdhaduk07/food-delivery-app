package food_delivery_app.services;

import food_delivery_app.cart.Cart;
import food_delivery_app.discount.DiscountStrategy;
import food_delivery_app.model.*;
import food_delivery_app.payment.PaymentContext;
import food_delivery_app.repository.OrderRepository;

import java.util.List;

public class OrderService {

    private final OrderRepository orderRepo;
    private final CartService cartService;
    private final DeliveryService deliveryService;
    private final InvoiceService invoiceService = new InvoiceService();

    public OrderService() {
        this.orderRepo = OrderRepository.getInstance();
        this.cartService = new CartService();
        this.deliveryService = new DeliveryService();
    }

    //place order
    public Order placeOrder(Customer customer, Address deliveryAddress, PaymentContext paymentContext, DiscountStrategy discountStrategy) throws InterruptedException {

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

        //apply discount
        double finalAmount = order.getTotalAmount();

        if (discountStrategy != null) {
            finalAmount = discountStrategy.applyDiscount(finalAmount);
        }

        //payment
        order.setStatus(IOrderStatus.PAYMENT_PENDING);
        System.out.println("Payment Processing ...");
        Thread.sleep(2000);


        boolean paymentSuccess = paymentContext.executePayment(finalAmount);

        if (!paymentSuccess) {
            System.out.println("Payment failed.");
            return null;
        }
        else {
            System.out.println("Success!");
        }
        //confirm order
        order.setStatus(IOrderStatus.CONFIRMED);
        System.out.println("Order placed successfully.");

        //save order
        orderRepo.addOrder(order);

        //assign delivey boy
        Thread.sleep(2000);
        deliveryService.assignDeliveryBoy(order);

        //clear cart
        cartService.clearCart(cart);



        //print invoice
        System.out.println("Invoice generating... ");
        Thread.sleep(2000);
        invoiceService.generateInvoice(order, finalAmount);

        return order;
    }

    public Order getOrder(int orderId) {
        return orderRepo.findById(orderId);
    }
}