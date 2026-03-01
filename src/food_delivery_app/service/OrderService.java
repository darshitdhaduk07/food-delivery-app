package food_delivery_app.service;

import food_delivery_app.model.cart.Cart;
import food_delivery_app.model.discount.Discount;
import food_delivery_app.exception.PaymentFailedException;
import food_delivery_app.model.*;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.order.Order;
import food_delivery_app.model.order.OrderItem;
import food_delivery_app.payment.PaymentMethod;
import food_delivery_app.payment.PaymentProcessor;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;
import food_delivery_app.utility.InputValidator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderService {

    private final OrderRepository orderRepo;
    private final DeliveryRepository deliveryRepository;
    private final CartService cartService;
    private final InvoiceService invoiceService = new InvoiceService();
    private final NotificationService notificationService ;
    private final ExecutorService executor;
    private static DeliveryAgentService deliveryAgentService;
    private final Map<Integer, CompletableFuture<Void>> orderChains
            = new ConcurrentHashMap<>();

    public OrderService() {
        this.orderRepo = OrderRepository.getInstance();
        this.cartService = new CartService();
        this.deliveryRepository = DeliveryRepository.getInstance();
        this.notificationService = new NotificationService();
        this.executor = Executors.newFixedThreadPool(2);
    }
    public static void setDeliveryAgentService(DeliveryAgentService service) {
        deliveryAgentService = service;
    }
    public boolean processPayment(double amount) {
        int i = 1;
        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.print(i + ".");
            System.out.println(method);
            i++;
        }
        int choice = InputValidator.readInt("Enter your choice for payment: ", 1, PaymentMethod.values().length);
        PaymentProcessor processor = new PaymentProcessor();
        return processor.process(PaymentMethod.values()[choice - 1], amount);


    }

    //place order
    public void placeOrder(Customer customer, Address deliveryAddress, Discount d) throws InterruptedException, PaymentFailedException {
        Cart cart = customer.getCart();
        //validate cart

        //take order item
        List<OrderItem> orderItems = cartService.convertToOrderItems(cart);

        //create order
        Order order = new Order(customer, orderItems, deliveryAddress);
        nextStatusAsync(order,0);

        //apply discount
        double finalAmount = order.getTotalAmount();
        double discount = d.getDiscountRate()/100.0;
        if(finalAmount >= d.getMinAmount())
            order.setFinalAmount(finalAmount-(finalAmount*discount));


        //payment
        boolean paymentSuccess = processPayment(finalAmount - (finalAmount * discount));
        System.out.println("\nPayment Processing ...\n");
        Thread.sleep(2000);

        if (!paymentSuccess) {
            throw new PaymentFailedException("Payment failed. Order not placed.");
        } else {
            System.out.println("Success!\n");
        }


        //confirm order
        nextStatusAsync(order,0);
        System.out.println("Order placed successfully.\n");

        //save order
        orderRepo.addOrderToHistory(order);

        //preparing your order
        nextStatusAsync(order,10);
        DeliveryAgent agent = deliveryAgentService.assignDeliveryAgent(order);
        if (agent != null) {
            order.assignDeliveryAgent(agent);
        } else orderRepo.addOrderToPending(order);

        //clear cart
        cartService.clearCart(cart);


        //print invoice
        System.out.println("\nInvoice generating... \n");
        Thread.sleep(2000);
        invoiceService.generateInvoice(order, finalAmount, discount);

    }
    public void nextStatusAsync(Order order, int delaySec) {

        CompletableFuture<Void> last =
                orderChains.getOrDefault(
                        order.getId(),
                        CompletableFuture.completedFuture(null)
                );

        CompletableFuture<Void> newTask =
                last.thenRunAsync(() -> {

                    try {
                        Thread.sleep(delaySec * 1000L);

                        order.moveToNextState();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }, executor);

        newTask.whenComplete((r,e) ->
                orderChains.remove(order.getId())
        );

        orderChains.put(order.getId(), newTask);
    }

    public Order getOrder(int orderId) {
        return orderRepo.findById(orderId);
    }
}