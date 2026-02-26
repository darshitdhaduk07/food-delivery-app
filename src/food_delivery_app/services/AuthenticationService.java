package food_delivery_app.services;

import food_delivery_app.cart.Cart;
import food_delivery_app.model.Customer;
import food_delivery_app.model.DeliveryBoy;
import food_delivery_app.model.Manager;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.utility.InputValidator;

public class AuthenticationService {
    private final CustomerRepository customerRepo =
            CustomerRepository.getInstance();

    private final DeliveryRepository deliveryRepo =
            DeliveryRepository.getInstance();


    public Customer loginCustomer() {
        String email =
                InputValidator.readEmail("Email: ");

        int password =
                InputValidator.readInt("Password: ");

        Customer customer =
                customerRepo.findByEmail(email);

        if (customer != null &&
                customer.getPassword() == password)
            return customer;

        return null;
    }
//    public Manager loginManager()
//    {
//        String email =
//                InputValidator.readEmail("Email: ");
//
//        int password =
//                InputValidator.readInt("Password: ");
//        Manager manager =
//    }

    public void registerCustomer() {
        String name =
                InputValidator.readStringOnlyLetters("Name: ");

        String email =
                InputValidator.readEmail("Email: ");

        int password =
                InputValidator.readInt("Password: ");

        if (customerRepo.findByEmail(email) != null) {
            System.out.println("Email already exists.");
            return;
        }

        Customer customer =
                new Customer(name,email,password);

        customer.setCart(new Cart());

        customerRepo.addCustomer(customer);

    }

    public DeliveryBoy loginDelivery() {

        String email =
                InputValidator.readEmail("Email: ");
        int password =
                InputValidator.readInt("Password: ");

        for (DeliveryBoy boy : deliveryRepo.findAll()) {

            if (boy.getEmail().equals(email)
                    && boy.getPassword() == password)
                return boy;
        }

        return null;
    }
}
